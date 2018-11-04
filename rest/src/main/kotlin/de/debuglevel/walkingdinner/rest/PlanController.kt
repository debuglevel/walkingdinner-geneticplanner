package de.debuglevel.walkingdinner.rest

import de.debuglevel.walkingdinner.cli.performance.TimeMeasurement
import de.debuglevel.walkingdinner.geneticplanner.CoursesProblem
import de.debuglevel.walkingdinner.geneticplanner.GeneticPlanner
import de.debuglevel.walkingdinner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.importer.Database
import de.debuglevel.walkingdinner.model.team.Team
import de.debuglevel.walkingdinner.report.teams.summary.SummaryReporter
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.stat.DoubleMomentStatistics
import mu.KotlinLogging
import spark.Request
import spark.kotlin.RouteHandler
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer
import javax.servlet.MultipartConfigElement
import javax.servlet.http.Part

object PlanController {
    private val logger = KotlinLogging.logger {}

    private val plans = mutableMapOf<Int, Future<EvolutionResult<EnumGene<Team>, Double>?>>()
    private val nextPlanId = AtomicInteger()

    private val executor = Executors.newFixedThreadPool(4)

    fun postOne(): RouteHandler.() -> Any {
        return {
            logger.debug("Got POST request on '/plans' with content-type '${request.contentType()}'")

            if (!request.contentType().startsWith("multipart/form-data")) {
                logger.debug { "Declining POST request with unsupported content-type '${request.contentType()}'" }
                throw Exception("Content-Type ${request.contentType()} not supported.")
            }

            // get supplied multipart values
            val surveyCsvFile = getMultipartFile(request, "surveyfile")
            val populationsSize = getMultipartField(request, "populationsSize").toInt()
            val fitnessThreshold = getMultipartField(request, "fitnessThreshold").toDouble()
            val steadyFitness = getMultipartField(request, "steadyFitness").toInt()
            val location = getMultipartField(request, "location")

            // start planner
            val plannerTask = Callable<EvolutionResult<EnumGene<Team>, Double>?> {
                val startPlanner = try {
                    startPlanner(surveyCsvFile, populationsSize, fitnessThreshold, steadyFitness, location)
                } catch (e: Exception) {
                    logger.error("Callable threw exception", e)
                    throw e
                }

                startPlanner
            }

            val plannerFuture = executor.submit(plannerTask)
            val planId = nextPlanId.getAndIncrement()
            plans[planId] = plannerFuture

            "Computing plan /plans/$planId ..."
        }
    }

    /**
     * Get text from a multipart request.
     *
     * @param fieldName name of the field in the HTML form
     */
    private fun getMultipartField(request: Request, fieldName: String) =
            request.raw()
                    .getPart(fieldName)
                    .inputStream
                    .reader()
                    .use { it.readText() }

    /**
     * Get a file from a multipart request.
     *
     * Copies the content form a multipart request field to a file and returns the path.
     * Note: The file should be deleted after usage.
     *
     * @param fieldName name of the field in the HTML form
     */
    private fun getMultipartFile(request: Request, fieldName: String): Path {
        val temporarySurveyFile = createTempFile("walkingdinner-plan").toPath()
        request.attribute("org.eclipse.jetty.multipartConfig", MultipartConfigElement("/temp"))
        request.raw()
                .getPart(fieldName) // getPart needs to use same "name" as input field in form
                .inputStream
                .use {
                    Files.copy(it, temporarySurveyFile, StandardCopyOption.REPLACE_EXISTING)
                }
        logger.debug("Uploaded file '${getOriginalFilename(request.raw().getPart(fieldName))}' saved as '${temporarySurveyFile.toAbsolutePath()}'")
        return temporarySurveyFile
    }

    /**
     * Get the original file name of a multipart part
     */
    private fun getOriginalFilename(part: Part): String? {
        for (cd in part.getHeader("content-disposition").split(";")) {
            if (cd.trim { it <= ' ' }.startsWith("filename")) {
                return cd.substring(
                        cd
                                .indexOf('=') + 1)
                        .trim { it <= ' ' }
                        .replace("\"", "")
            }
        }

        return null
    }

    private fun startPlanner(fileName: Path,
                             populationsSize: Int,
                             fitnessThreshold: Double,
                             steadyFitness: Int,
                             location: String): EvolutionResult<EnumGene<Team>, Double>? {
        val evolutionStatistics = EvolutionStatistics.ofNumber<Double>()
        val consumers: Consumer<EvolutionResult<EnumGene<Team>, Double>>? = Consumer {
            evolutionStatistics.accept(it)
            printIntermediary(it)
        }

        val database = Database(fileName, location)

        val options = GeneticPlannerOptions(
                evolutionResultConsumer = consumers,
                database = database,
                populationsSize = populationsSize,
                fitnessThreshold = fitnessThreshold,
                steadyFitness = steadyFitness
        )

        val result = GeneticPlanner(options).run()

        processResults(result, evolutionStatistics)

        return result
    }

    private fun processResults(result: EvolutionResult<EnumGene<Team>, Double>,
                               evolutionStatistics: EvolutionStatistics<Double, DoubleMomentStatistics>?) {
        println()
        println("Best in Generation: " + result.generation)
        println("Best with Fitness: " + result.bestFitness)

        println()
        println(evolutionStatistics)

        println()
        val courses = CoursesProblem(result.bestPhenotype.genotype.gene.validAlleles)
                .codec()
                .decode(result.bestPhenotype.genotype)
        val meetings = courses.toMeetings()

        SummaryReporter().generateReports(meetings)
//        GmailDraftReporter().generateReports(meetings)
    }

    private fun printIntermediary(e: EvolutionResult<EnumGene<Team>, Double>) {
        TimeMeasurement.add("evolveDuration", e.durations.evolveDuration.toNanos(), 500)
        if (e.generation % 500 == 0L) {
            println("${Math.round(1 / (e.durations.evolveDuration.toNanos() / 1_000_000_000.0))}gen/s\t| Generation: ${e.generation}\t| Best Fitness: ${e.bestFitness}")
        }
    }

    fun getOne(): RouteHandler.() -> String {
        return {
            val id = request.params(":id").toInt()
            logger.debug("Got GET request on '/plan/$id'")

            val future = plans[id]

            val resultGeneration = if (future?.isDone == true) {
                future.get()?.generation
            } else {
                "not ready yet"
            }

            "Result generated in generation: $resultGeneration"
        }
    }

    fun getFormHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/plan'")

            val html = """
                <form method='post' enctype='multipart/form-data'>

                    <fieldset>
                        <legend>Anmeldungen:</legend>
                        <label for="surveyfile">Umfrage als CSV</label>
                        <br>
                        <input type='file' name='surveyfile' accept='.csv'>
                        <br>

                        <label for="location">Stadt</label>
                        <br>
                        <input type='text' name='location' value='Bamberg, Germany'>
                        <br>
                    </fieldset>

                    <fieldset>
                        <legend>Optionen für Genetischen Algorithmus:</legend>
                        <label for="populationsSize">Population Size</label>
                        <br>
                        <input type='number' name='populationsSize' value='200'>
                        <br>

                        <label for="fitnessThreshold">Fitness Threshold</label>
                        <br>
                        <input type='number' name='fitnessThreshold' value='0.001'>
                        <br>

                        <label for="steadyFitness">Steady Fitness</label>
                        <br>
                        <input type='number' name='steadyFitness' value='40000'>
                        <br>
                    </fieldset>

                    <button>Berechnung starten</button>
                </form>
            """

            html
        }
    }
}
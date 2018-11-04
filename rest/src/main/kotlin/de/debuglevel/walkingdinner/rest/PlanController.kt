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
import spark.kotlin.RouteHandler
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
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

    private val planMap = mutableMapOf<Int, Future<EvolutionResult<EnumGene<Team>, Double>?>>()
    private val nextPlanMapId = AtomicInteger()

    fun postOne(): RouteHandler.() -> Any {
        return {
            if (!request.contentType().startsWith("multipart/form-data")) {
                throw Exception("Content-Type ${request.contentType()} not supported.")
            }

            logger.debug("Got POST request on '/plan'")

            // TODO: executor should probably not be defined in a local per-request scope.
            logger.debug("Creating executor...")
            val executor = Executors.newFixedThreadPool(10)

            val uploadDir = File("upload")
            uploadDir.mkdir() // create the upload directory if it doesn't exist

            // get provided CSV file
            val tempFile = Files.createTempFile(uploadDir.toPath(), "", "")

            request.attribute("org.eclipse.jetty.multipartConfig", MultipartConfigElement("/temp"))

            request.raw()
                    .getPart("surveyfile") // getPart needs to use same "name" as input field in form
                    .inputStream
                    .use {
                        Files.copy(it, tempFile, StandardCopyOption.REPLACE_EXISTING)
                    }

            //val raw = request.raw().parts
            val populationsSize = request.raw().getPart("populationsSize").inputStream.reader().use { it.readText() }.toInt()
            val fitnessThreshold = request.raw().getPart("fitnessThreshold").inputStream.reader().use { it.readText() }.toDouble()
            val steadyFitness = request.raw().getPart("steadyFitness").inputStream.reader().use { it.readText() }.toInt()

            logger.debug("Uploaded file '" + getOriginalFilename(request.raw().getPart("surveyfile")) + "' saved as '" + tempFile.toAbsolutePath() + "'")

            val callableTask = Callable<EvolutionResult<EnumGene<Team>, Double>?> {
                val startPlanner = try {
                    startPlanner(tempFile, populationsSize, fitnessThreshold, steadyFitness)
                } catch (e: Exception) {
                    logger.error("Callable died: $e")
                    throw e
                }

                startPlanner
            }

            val future = executor.submit(callableTask)
            val id = nextPlanMapId.getAndIncrement()
            planMap[id] = future

            // do things with result


            "done, computing plan $id"
        }
    }

    private fun getOriginalFilename(part: Part): String? {
        for (cd in part.getHeader("content-disposition").split(";")) {
            if (cd.trim { it <= ' ' }.startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim { it <= ' ' }.replace("\"", "")
            }
        }

        return null
    }

    private fun startPlanner(fileName: Path, populationsSize: Int, fitnessThreshold: Double, steadyFitness: Int): EvolutionResult<EnumGene<Team>, Double>? {
        val evolutionStatistics = EvolutionStatistics.ofNumber<Double>()
        val consumers: Consumer<EvolutionResult<EnumGene<Team>, Double>>? = Consumer {
            evolutionStatistics.accept(it)
            printIntermediary(it)
        }

        val database = buildDatabase(fileName)

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

    private fun buildDatabase(fileName: Path): Database {
        val csvFilename = fileName.toAbsolutePath().toString()
        val csvUrl = Paths.get(csvFilename).toUri().toURL()
        val database = Database(csvUrl)
        database.initialize()

        return database
    }

    private fun processResults(result: EvolutionResult<EnumGene<Team>, Double>, evolutionStatistics: EvolutionStatistics<Double, DoubleMomentStatistics>?) {
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

            val future = planMap[id]

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
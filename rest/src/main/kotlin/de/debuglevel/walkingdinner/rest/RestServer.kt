package de.debuglevel.walkingdinner.rest

import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.stat.DoubleMomentStatistics
import de.debuglevel.walkingdinner.cli.performance.TimeMeasurement
import de.debuglevel.walkingdinner.geneticplanner.CoursesProblem
import de.debuglevel.walkingdinner.geneticplanner.GeneticPlanner
import de.debuglevel.walkingdinner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.importer.Database
import de.debuglevel.walkingdinner.model.team.Team
import de.debuglevel.walkingdinner.report.teams.summary.SummaryReporter
import mu.KotlinLogging
import spark.kotlin.*
import java.nio.file.Paths
import java.util.function.Consumer
import java.nio.file.StandardCopyOption
import javax.servlet.MultipartConfigElement
import java.io.IOException
import java.nio.file.Files
import spark.Spark.staticFiles
import spark.Request
import spark.Spark
import java.io.File
import java.nio.file.Path
import javax.servlet.ServletException
import javax.servlet.http.*
import java.io.PrintWriter
import java.io.StringWriter
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger


fun main(args: Array<String>) {
    RestServer().main(args)
}

class RestServer {
    private val logger = KotlinLogging.logger {}

    val planMap = mutableMapOf<Int, Future<EvolutionResult<EnumGene<Team>, Double>?>>()
    val nextPlanMapId = AtomicInteger()

    fun main(args: Array<String>) {
        logger.debug("Starting REST...")

        logger.debug("Assigning port...")
        port(getHerokuAssignedPort());
        logger.debug("Assigning port done")

        logger.debug("Creating executor...")
        val executor = Executors.newFixedThreadPool(10)

        val uploadDir = File("upload")
        uploadDir.mkdir() // create the upload directory if it doesn't exist

        staticFiles.externalLocation("upload")

        // TODO: return immediately and return the link to the upcoming planning result
        post("/plan") {
            logger.debug("Got POST request on '/plan'")

            // get provided CSV file
            val tempFile = Files.createTempFile(uploadDir.toPath(), "", "")

            request.attribute("org.eclipse.jetty.multipartConfig", MultipartConfigElement("/temp"))

            request.raw()
                    .getPart("surveyfile") // getPart needs to use same "name" as input field in form
                    .getInputStream()
                    .use({
                        Files.copy(it, tempFile, StandardCopyOption.REPLACE_EXISTING)
                    })

            val raw = request.raw().parts
            val populationsSize = request.raw().getPart("populationsSize").inputStream.reader().use { it.readText() }.toInt()
            val fitnessThreshold = request.raw().getPart("fitnessThreshold").inputStream.reader().use { it.readText() }.toDouble()
            val steadyFitness = request.raw().getPart("steadyFitness").inputStream.reader().use { it.readText() }.toInt()

            logInfo(request, tempFile)


            val callableTask = Callable<EvolutionResult<EnumGene<Team>, Double>?> {
                val startPlanner = try {
                    startPlanner(tempFile, populationsSize, fitnessThreshold, steadyFitness)
                }
                catch (e: Exception)
                {
                    logger.error("Callable died: $e")
                    throw e
                }

                startPlanner
            }

            val future = executor.submit(callableTask)
            val id = nextPlanMapId.getAndIncrement()
            planMap.put(id, future)

            // do things with result


            "done, computing plan $id"
        }

        // TODO: /plan/$id which returns the calculated plan;
        // return some other http code to indicate that the plan is not ready now

        get("/plan/:id")
        {
            val id = request.params(":id").toInt()
            logger.debug("Got GET request on '/plan/$id'")

            val future = planMap.get(id)

            val resultGeneration = if (future?.isDone == true) {
                val result = future?.get()?.generation
            }else { "not ready yet" }

//            val resultGeneration = result?.generation ?: "not yet"

            "Result generated in generation $resultGeneration"
        }

        // TODO: add (optional) config stuff
        get("/plan")
        {
            logger.debug("Got GET request on '/plan'")

            """
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
        }

        Spark.exception(Exception::class.java, { e, request, response ->
            val sw = StringWriter()
            val pw = PrintWriter(sw, true)
            e.printStackTrace(pw)
            System.err.println(sw.buffer.toString())
        })

        internalServerError {
            response.type("application/json")
            "{\"message\":\"Custom 500 handling\"}"
        }

        logger.debug("listening...")
    }

    fun getHerokuAssignedPort(): Int {
        val processBuilder = ProcessBuilder()
        return if (processBuilder.environment()["PORT"] != null) {
            logger.debug("Using port "+Integer.parseInt(processBuilder.environment()["PORT"]))
            Integer.parseInt(processBuilder.environment()["PORT"])
        } else {
            logger.debug("Using port 4567")
            4567
        }
        //return default port if heroku-port isn't set (i.e. on localhost)
    }

    // methods used for logging
    @Throws(IOException::class, ServletException::class)
    private fun logInfo(req: Request, tempFile: Path) {
        logger.debug("Uploaded file '" + getFileName(req.raw().getPart("surveyfile")) + "' saved as '" + tempFile.toAbsolutePath() + "'")
    }

    private fun getFileName(part: Part): String? {
        for (cd in part.getHeader("content-disposition").split(";")) {
            if (cd.trim({ it <= ' ' }).startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim({ it <= ' ' }).replace("\"", "")
            }
        }
        return null
    }

    fun startPlanner(fileName: Path, populationsSize: Int, fitnessThreshold: Double, steadyFitness: Int): EvolutionResult<EnumGene<Team>, Double>? {
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
}
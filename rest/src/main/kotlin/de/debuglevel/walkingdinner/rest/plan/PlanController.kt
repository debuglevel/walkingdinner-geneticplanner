package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.cli.performance.TimeMeasurement
import de.debuglevel.walkingdinner.importer.Database
import de.debuglevel.walkingdinner.model.Plan
import de.debuglevel.walkingdinner.model.team.Team
import de.debuglevel.walkingdinner.planner.geneticplanner.CoursesProblem
import de.debuglevel.walkingdinner.planner.geneticplanner.GeneticPlanner
import de.debuglevel.walkingdinner.planner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.report.teams.summary.SummaryReporter
import de.debuglevel.walkingdinner.rest.MultipartUtils
import de.debuglevel.walkingdinner.rest.responsetransformer.JsonTransformer
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.stat.DoubleMomentStatistics
import mu.KotlinLogging
import spark.ModelAndView
import spark.kotlin.RouteHandler
import spark.template.mustache.MustacheTemplateEngine
import java.nio.file.Path
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

object PlanController {
    private val logger = KotlinLogging.logger {}

    private val plans = mutableMapOf<Int, Future<Plan>>()
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
            val surveyCsvFile = MultipartUtils.getFile(request, "surveyfile")
            val populationsSize = MultipartUtils.getField(request, "populationsSize").toInt()
            val fitnessThreshold = MultipartUtils.getField(request, "fitnessThreshold").toDouble()
            val steadyFitness = MultipartUtils.getField(request, "steadyFitness").toInt()
            val location = MultipartUtils.getField(request, "location")

            // start planner
            val plannerTask = Callable<Plan> {
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

    private fun startPlanner(fileName: Path,
                             populationsSize: Int,
                             fitnessThreshold: Double,
                             steadyFitness: Int,
                             location: String): Plan {
        val evolutionStatistics = EvolutionStatistics.ofNumber<Double>()
        val consumers: Consumer<EvolutionResult<EnumGene<Team>, Double>>? = Consumer {
            evolutionStatistics.accept(it)
            printIntermediary(it)
        }

        val database = Database(fileName, location)

        val options = GeneticPlannerOptions(
                evolutionResultConsumer = consumers,
                teams = database.teams,
                populationsSize = populationsSize,
                fitnessThreshold = fitnessThreshold,
                steadyFitness = steadyFitness
        )

        val plan = GeneticPlanner(options).plan()

        //processResults(result, evolutionStatistics)

        return plan
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
            val dinnerId = request.params(":dinnerId").toInt()
            val planId = request.params(":planId").toInt()
            logger.debug("Got GET request on '/dinner/$dinnerId/plans/$planId'")

            val future = plans[planId]

            if (future == null) {
                type(contentType = "plain/text")
                status(404)
                "Plan not found"
            } else {
                type(contentType = "application/json")
                val plan = when {
                    future.isDone -> PlanDTO(true, future.get())
                    else -> PlanDTO(false)
                }
                JsonTransformer.render(plan)
            }
        }
    }

    fun getAddFormHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/plans'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "plan/add.html.mustache"))
        }
    }
}
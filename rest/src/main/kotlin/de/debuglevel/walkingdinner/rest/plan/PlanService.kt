package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.cli.performance.TimeMeasurement
import de.debuglevel.walkingdinner.importer.Database
import de.debuglevel.walkingdinner.planner.geneticplanner.CoursesProblem
import de.debuglevel.walkingdinner.planner.geneticplanner.GeneticPlanner
import de.debuglevel.walkingdinner.planner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.report.teams.summary.SummaryReporter
import de.debuglevel.walkingdinner.rest.participant.Team
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.stat.DoubleMomentStatistics
import mu.KotlinLogging
import java.nio.file.Path
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.function.Consumer
import javax.inject.Singleton

@Singleton
class PlanService {
    private val logger = KotlinLogging.logger {}

    fun get(planId: UUID): PlanDTO? {
        return getX(planId)
        //return PlanDTO(true, null)
    }

    fun getAll(): Set<PlanDTO> {
        return (1..5)
            .map {
                PlanDTO(
                    true,
                    null
                )
            }
            .toSet()
    }

    private val executor = Executors.newFixedThreadPool(4)

    // TODO: should not be public
    val plans = mutableMapOf<UUID, Future<Plan>>()

    fun getX(planId: UUID): PlanDTO? {
//            type(contentType = "application/json")
//            val dinnerId = request.params(":dinnerId").toUUID()
        //val planId = request.params(":planId").toUUID()
//        val planId = UUID.randomUUID()

        val future = plans[planId]

        return if (future == null) {
//                type(contentType = "plain/text")
//                status(404)
            //"Plan not found"
            null
            // TODO: throw exception instead
        } else {
//                type(contentType = "application/json")
            val plan = when {
                future.isDone -> PlanDTO(true, future.get())
                else -> PlanDTO(false)
            }
//            JsonTransformer.render(plan)
            plan
        }
    }

    fun startPlannerX(
        surveyCsvFile: Path,
        populationsSize: Int,
        fitnessThreshold: Double,
        steadyFitness: Int,
        location: String
    ): UUID {
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
        val planId = UUID.randomUUID()
        plans[planId] = plannerFuture
        return planId
    }

    private fun startPlanner(
        fileName: Path,
        populationsSize: Int,
        fitnessThreshold: Double,
        steadyFitness: Int,
        location: String
    ): Plan {
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

    private fun processResults(
        result: EvolutionResult<EnumGene<Team>, Double>,
        evolutionStatistics: EvolutionStatistics<Double, DoubleMomentStatistics>?
    ) {
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
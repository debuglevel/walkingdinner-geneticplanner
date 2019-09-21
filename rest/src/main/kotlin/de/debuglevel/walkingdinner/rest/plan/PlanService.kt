package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.cli.performance.TimeMeasurement
import de.debuglevel.walkingdinner.rest.dinner.planner.geneticplanner.CoursesProblem
import de.debuglevel.walkingdinner.rest.dinner.planner.geneticplanner.GeneticPlanner
import de.debuglevel.walkingdinner.rest.dinner.planner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.rest.dinner.report.teams.summary.SummaryReporter
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.importer.Database
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.stat.DoubleMomentStatistics
import io.micronaut.context.annotation.Property
import mu.KotlinLogging
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.function.Consumer
import javax.inject.Singleton

@Singleton
class PlanService(
    @Property(name = "app.walkingdinner.planners.threads") val threadCount: Int
) {
    private val logger = KotlinLogging.logger {}

    fun get(planId: UUID): PlanDTO {
        val future = plans[planId]
        return if (future == null) {
            throw PlanNotFoundException(planId)
        } else {
            when {
                future.isDone -> PlanDTO(true, future.get())
                else -> PlanDTO(false)
            }
        }
    }

    fun getAll(): Set<PlanDTO> {
        return plans
            .map { get(it.key) }
            .toSet()
    }

    private val executor = Executors.newFixedThreadPool(threadCount)

    private val plans = mutableMapOf<UUID, Future<Plan>>()

    fun startPlannerAsync(
        surveyCsv: String,
        populationsSize: Int,
        fitnessThreshold: Double,
        steadyFitness: Int,
        location: String
    ): UUID {
        val plannerTask = Callable<Plan> {
            try {
                startPlanner(surveyCsv, populationsSize, fitnessThreshold, steadyFitness, location)
            } catch (e: Exception) {
                logger.error("Callable threw exception", e)
                throw e
            }
        }

        val planFuture = executor.submit(plannerTask)
        val planId = UUID.randomUUID()
        plans[planId] = planFuture
        return planId
    }

    private fun startPlanner(
        surveyCsv: String,
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

        val database = Database(surveyCsv, location)

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

    class PlanNotFoundException(planId: UUID) : Exception("Plan $planId not found")
}
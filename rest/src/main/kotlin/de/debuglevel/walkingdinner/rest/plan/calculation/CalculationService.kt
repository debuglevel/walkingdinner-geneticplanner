package de.debuglevel.walkingdinner.rest.plan.calculation

import de.debuglevel.walkingdinner.cli.performance.TimeMeasurement
import de.debuglevel.walkingdinner.rest.dinner.planner.geneticplanner.GeneticPlanner
import de.debuglevel.walkingdinner.rest.dinner.planner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.importer.Database
import de.debuglevel.walkingdinner.rest.plan.Plan
import de.debuglevel.walkingdinner.rest.plan.PlanService
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.micronaut.context.annotation.Property
import mu.KotlinLogging
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.function.Consumer
import javax.inject.Singleton

@Singleton
class CalculationService(
    @Property(name = "app.walkingdinner.planners.threads") val threadCount: Int,
    private val planService: PlanService
) {
    private val logger = KotlinLogging.logger {}

    private val executor = Executors.newFixedThreadPool(threadCount)

    // TODO: remove this; should be accessed via calculations
    //private val futurePlans = mutableMapOf<UUID, Future<Plan>>()

    private val calculations = mutableMapOf<UUID, Calculation>()

    fun get(id: UUID): Calculation {
        logger.debug { "Getting calculation '$id'..." }
        val calculation = calculations.getOrElse(id) { throw CalculationNotFoundException(id) }
        logger.debug { "Got calculation: $calculation" }
        return calculation
    }

    fun getAll(): Set<Calculation> {
        logger.debug { "Getting all calculations..." }
        val calculations = calculations
            .map { get(it.key) }
            .toSet()
        logger.debug { "Got calculation: $calculations" }
        return calculations
    }

    fun startCalculation(
        surveyCsv: String,
        populationsSize: Int,
        fitnessThreshold: Double,
        steadyFitness: Int,
        location: String
    ): Calculation {
        val calculation = Calculation(UUID.randomUUID(), false, null)

        val plannerTask = Callable<Plan> {
            try {
                calculatePlan(surveyCsv, populationsSize, fitnessThreshold, steadyFitness, location, calculation)
            } catch (e: Exception) {
                logger.error(e) { "Callable threw exception" }
                throw e
            }
        }

        executor.submit(plannerTask)

        calculations[calculation.id] = calculation
        return calculation

//        val futurePlan = executor.submit(plannerTask)
//        val planId = UUID.randomUUID()
//        futurePlans[planId] = futurePlan
//        return planId
    }

    private fun calculatePlan(
        surveyCsv: String,
        populationsSize: Int,
        fitnessThreshold: Double,
        steadyFitness: Int,
        location: String,
        calculation: Calculation
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

        calculation.calculationFinished = true
        calculation.plan = plan

        //processResults(result, evolutionStatistics)

        return plan
    }

//    private fun processResults(
//        result: EvolutionResult<EnumGene<Team>, Double>,
//        evolutionStatistics: EvolutionStatistics<Double, DoubleMomentStatistics>?
//    ) {
//        println()
//        println("Best in Generation: " + result.generation)
//        println("Best with Fitness: " + result.bestFitness)
//
//        println()
//        println(evolutionStatistics)
//
//        println()
//        val courses = CoursesProblem(result.bestPhenotype.genotype.gene.validAlleles)
//            .codec()
//            .decode(result.bestPhenotype.genotype)
//        val meetings = courses.toMeetings()
//
//        SummaryReporter().generateReports(meetings)
////        GmailDraftReporter().generateReports(meetings)
//    }

    private fun printIntermediary(e: EvolutionResult<EnumGene<Team>, Double>) {
        TimeMeasurement.add("evolveDuration", e.durations.evolveDuration.toNanos(), 500)
        if (e.generation % 500 == 0L) {
            println("${Math.round(1 / (e.durations.evolveDuration.toNanos() / 1_000_000_000.0))}gen/s\t| Generation: ${e.generation}\t| Best Fitness: ${e.bestFitness}")
        }
    }

    class CalculationNotFoundException(planId: UUID) : Exception("Plan $planId not found")
}
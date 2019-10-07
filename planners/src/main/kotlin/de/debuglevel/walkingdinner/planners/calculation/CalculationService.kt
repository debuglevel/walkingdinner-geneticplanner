package de.debuglevel.walkingdinner.planners.calculation

import de.debuglevel.walkingdinner.planners.plan.Plan
import de.debuglevel.walkingdinner.planners.plan.PlanService
import de.debuglevel.walkingdinner.planners.planner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.planners.planner.geneticplanner.GeneticPlannerService
import io.micronaut.context.annotation.Property
import mu.KotlinLogging
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import javax.inject.Singleton

@Singleton
class CalculationService(
    private val geneticPlannerService: GeneticPlannerService,
    @Property(name = "app.walkingdinner.calculations.max-concurrent") private val threadCount: Int,
    private val planService: PlanService
) {
    private val logger = KotlinLogging.logger {}

    private val executor = Executors.newFixedThreadPool(threadCount)

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

    fun start(
        calculation: Calculation
    ): Calculation {
        val uuid = UUID.randomUUID()
        calculation.id = uuid
        logger.debug { "Starting calculation $calculation..." }

        calculations[uuid] = calculation

        val calculatePlanTask = Callable<Plan> {
            try {
                val geneticPlannerOptions =
                    GeneticPlannerOptions(
                        teams = calculation.teams,
                        fitnessThreshold = calculation.fitnessThreshold,
                        populationsSize = calculation.populationsSize,
                        steadyFitness = calculation.steadyFitness
                    )

                calculation.begin = LocalDateTime.now()
                val plan = geneticPlannerService.calculatePlan(geneticPlannerOptions)
                calculation.end = LocalDateTime.now()
                calculation.finished = true

                calculation.plan = plan
                planService.add(plan)

                plan
            } catch (e: Exception) {
                logger.error(e) { "Callable threw exception" }
                throw e
            }
        }

        executor.submit(calculatePlanTask)

        logger.debug { "Started calculation $calculation" }
        return calculation
    }

    class CalculationNotFoundException(planId: UUID) : Exception("Plan $planId not found")
}
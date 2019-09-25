package de.debuglevel.walkingdinner.rest.plan.calculation

import java.util.*

data class CalculationResponse(
    /**
     * UUID of the calculation
     */
    val id: UUID,
    /**
     * Whether the calculation of the plan has finished or is still in progress
     */
    val finished: Boolean,
    /**
     * Survey data as CSV to base the calculation on
     */
    val surveyfile: String,
    /**
     * Size of the population (for calculation with Genetic Algorithm)
     */
    val populationsSize: Int,
    /**
     * Fitness level to beat (minimization problem, i.e. fitness must be less than this threshold) (for calculation with Genetic Algorithm)
     */
    val fitnessThreshold: Double,
    /**
     * Number of generations with constant fitness level to stop further calculations (for calculation with Genetic Algorithm)
     */
    val steadyFitness: Int,
    /**
     * City name where the dinner is based on (to calculate distances correctly)
     */
    val location: String,
    /**
     * UUID of the plan, once it is calculated
     */
    val planId: UUID?
) {
    constructor(calculation: Calculation) :
            this(
                calculation.id,
                calculation.finished,
                calculation.surveyfile,
                calculation.populationsSize,
                calculation.fitnessThreshold,
                calculation.steadyFitness,
                calculation.location,
                calculation.plan?.id
            )
}

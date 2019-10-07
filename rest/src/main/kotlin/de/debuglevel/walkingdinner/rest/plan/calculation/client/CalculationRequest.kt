package de.debuglevel.walkingdinner.rest.plan.calculation.client

import de.debuglevel.walkingdinner.rest.plan.calculation.Calculation

data class CalculationRequest(
    /**
     * Size of the population
     */
    val populationsSize: Int,
    /**
     * Fitness level to beat (minimization problem, i.e. fitness must be less than this threshold)
     */
    val fitnessThreshold: Double,
    /**
     * Number of generations with constant fitness level to stop further calculations
     */
    val steadyFitness: Int,
    /**
     * Teams to calculate into the plan
     */
    val teams: List<TeamRequest>
) {
    constructor(calculation: Calculation) : this(
        populationsSize = calculation.populationsSize,
        steadyFitness = calculation.steadyFitness,
        fitnessThreshold = calculation.fitnessThreshold,
        teams = calculation.teams.map { TeamRequest(it) }
    )
}
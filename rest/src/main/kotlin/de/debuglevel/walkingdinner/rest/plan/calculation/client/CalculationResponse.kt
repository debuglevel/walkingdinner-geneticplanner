package de.debuglevel.walkingdinner.rest.plan.calculation.client

import java.time.LocalDateTime
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
     * Teams to calculate into the plan
     */
    val teams: List<TeamResponse>,
    /**
     * UUID of the plan, once it is calculated
     */
    val planId: UUID?,
    /**
     * When the calculation began
     */
    var begin: LocalDateTime? = null,
    /**
     * When the calculation finished
     */
    var end: LocalDateTime? = null
)

//fun Calculation.toCalculationResponse(): CalculationResponse {
//    return CalculationResponse(
//        id = this.id!!,
//        finished = this.finished,
//        fitnessThreshold = this.fitnessThreshold,
//        populationsSize = this.populationsSize,
//        steadyFitness = this.steadyFitness,
//        teams = this.teams.map { it.toTeamResponse() },
//        planId = this.plan?.id
//    )
//}
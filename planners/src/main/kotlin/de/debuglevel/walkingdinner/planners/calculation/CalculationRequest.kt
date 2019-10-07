package de.debuglevel.walkingdinner.planners.calculation

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
    fun toCalculation(): Calculation {
        return Calculation(
            id = null,
            teams = this.teams.map { it.toTeam() },
            steadyFitness = this.steadyFitness,
            populationsSize = this.populationsSize,
            fitnessThreshold = this.fitnessThreshold,
            finished = false,
            plan = null
        )
    }
}
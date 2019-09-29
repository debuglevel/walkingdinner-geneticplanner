package de.debuglevel.walkingdinner.rest.plan.calculation

data class CalculationRequest(
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
    val steadyFitness: Int
)

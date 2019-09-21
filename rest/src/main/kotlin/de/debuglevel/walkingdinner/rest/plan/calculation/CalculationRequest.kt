package de.debuglevel.walkingdinner.rest.plan.calculation

data class CalculationRequest(
    val surveyfile: String,
    val populationsSize: Int,
    val fitnessThreshold: Double,
    val steadyFitness: Int,
    val location: String
)

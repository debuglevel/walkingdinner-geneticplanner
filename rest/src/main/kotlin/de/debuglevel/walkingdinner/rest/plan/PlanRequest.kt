package de.debuglevel.walkingdinner.rest.plan

data class PlanRequest(
    val surveyfile: String,
    val populationsSize: Int,
    val fitnessThreshold: Double,
    val steadyFitness: Int,
    val location: String
)

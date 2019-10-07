package de.debuglevel.walkingdinner.planners.planner.geneticplanner

import de.debuglevel.walkingdinner.planners.Team
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import java.util.function.Consumer

data class GeneticPlannerOptions(
    val teams: List<Team>,
    val fitnessThreshold: Double,
    val steadyFitness: Int,
    val populationsSize: Int,
    var evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>? = null
)
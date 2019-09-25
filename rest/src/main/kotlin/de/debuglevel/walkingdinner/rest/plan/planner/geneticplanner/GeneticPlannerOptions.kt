package de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner

import de.debuglevel.walkingdinner.rest.participant.Team
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import java.util.function.Consumer

data class GeneticPlannerOptions(
    val evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>?,
    val populationsSize: Int,
    val fitnessThreshold: Double,
    val steadyFitness: Int,
    val teams: List<Team>
)
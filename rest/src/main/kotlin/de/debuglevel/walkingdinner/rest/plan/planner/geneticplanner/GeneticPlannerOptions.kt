package de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner

import de.debuglevel.walkingdinner.rest.participant.Team
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import java.util.function.Consumer

data class GeneticPlannerOptions(
    val teams: List<Team>,
    val fitnessThreshold: Double,
    val steadyFitness: Int,
    val populationsSize: Int,
    val evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>?
)
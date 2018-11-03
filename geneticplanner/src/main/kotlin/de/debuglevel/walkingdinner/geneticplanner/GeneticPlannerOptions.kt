package de.debuglevel.walkingdinner.geneticplanner

import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import de.debuglevel.walkingdinner.importer.Database
import de.debuglevel.walkingdinner.model.team.Team
import java.util.function.Consumer

data class GeneticPlannerOptions(val evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>?,
                                 val populationsSize: Int = 200,
                                 val fitnessThreshold: Double = 0.001,
                                 val steadyFitness: Int = 40_000,
                                 val database: Database) {
}
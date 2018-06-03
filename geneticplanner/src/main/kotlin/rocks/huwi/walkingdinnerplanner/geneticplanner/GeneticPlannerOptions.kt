package rocks.huwi.walkingdinnerplanner.geneticplanner

import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import rocks.huwi.walkingdinnerplanner.importer.Database
import rocks.huwi.walkingdinnerplanner.model.team.Team
import java.util.function.Consumer

public class GeneticPlannerOptions(val evolutionResultConsumer: Consumer<EvolutionResult<EnumGene<Team>, Double>>?,
                                   val populationsSize: Int = 200,
                                   val fitnessThreshold: Double = 0.001,
                                   val steadyFitness: Int = 40_000,
                                   val database: Database) {
}
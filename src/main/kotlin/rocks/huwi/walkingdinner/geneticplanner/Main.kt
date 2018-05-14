package rocks.huwi.walkingdinner.geneticplanner

import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics

fun main(args: Array<String>) {
    println("=== ${BuildVersion.buildTitle} ${BuildVersion.buildVersion} ===")

    val evolutionStatistics = EvolutionStatistics.ofNumber<Double>()

    val consumers: (EvolutionResult<EnumGene<Team>, Double>) -> Unit =
            {
                evolutionStatistics.accept(it)
                printIntermediary(it)
            }

    val result = GeneticPlanner(consumers).run()

    println()
    println("Best in Generation: " + result.generation)
    println("Best with Fitness: " + result.bestFitness)

    println()
    println(evolutionStatistics)

    println()
    CoursesProblem(result.bestPhenotype.genotype.gene.validAlleles)
            .codec()
            .decode(result.bestPhenotype.genotype)
            .print()
}

fun printIntermediary(e: EvolutionResult<EnumGene<Team>, Double>) {
    if (e.generation % 500 == 0L) {
        println("Generation: ${e.generation}\t| Best Fitness: ${e.bestFitness}\t| Worst Fitness: ${e.worstFitness}")
    }
}
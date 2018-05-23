package rocks.huwi.walkingdinner.geneticplanner

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import org.apache.commons.validator.routines.UrlValidator
import rocks.huwi.walkingdinner.geneticplanner.performance.TimeMeasurement
import rocks.huwi.walkingdinner.geneticplanner.report.TextfileReporter
import rocks.huwi.walkingdinner.geneticplanner.team.Team
import java.net.URL
import java.nio.file.Paths

class Cli : CliktCommand() {
    private val csvFilename by option(help = "URL or file name of CSV file")

    override fun run() {
        println("=== ${BuildVersion.buildTitle} ${BuildVersion.buildVersion} ===")

        val evolutionStatistics = EvolutionStatistics.ofNumber<Double>()

        val consumers: (EvolutionResult<EnumGene<Team>, Double>) -> Unit =
                {
                    evolutionStatistics.accept(it)
                    printIntermediary(it)
                }

        val csvFilename: String = this.csvFilename ?: "Teams_aufbereitet.csv"
        val csvUrl = when {
            UrlValidator().isValid(csvFilename) -> URL(csvFilename)
            else -> Paths.get(csvFilename).toUri().toURL()
        }

        val result = GeneticPlanner(csvUrl, consumers).run()

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

        TextfileReporter(Paths.get("reports")).generateReport(
                CoursesProblem(result.bestPhenotype.genotype.gene.validAlleles)
                        .codec()
                        .decode(result.bestPhenotype.genotype).toMeetings())
    }

    private fun printIntermediary(e: EvolutionResult<EnumGene<Team>, Double>) {
        TimeMeasurement.add("evolveDuration", e.durations.evolveDuration.toNanos(), 500)
        if (e.generation % 500 == 0L) {
            println("${Math.round(1 / (e.durations.evolveDuration.toNanos() / 1_000_000_000.0))}gen/s\t| Generation: ${e.generation}\t| Best Fitness: ${e.bestFitness}")
        }
    }
}

fun main(args: Array<String>) = Cli().main(args)

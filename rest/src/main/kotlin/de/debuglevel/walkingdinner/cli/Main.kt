//package de.debuglevel.walkingdinner.cli
//
//import com.github.ajalt.clikt.core.CliktCommand
//import com.github.ajalt.clikt.parameters.options.option
//import de.debuglevel.walkingdinner.cli.performance.TimeMeasurement
//import de.debuglevel.walkingdinner.rest.common.BuildVersion
//import de.debuglevel.walkingdinner.rest.participant.Team
//import de.debuglevel.walkingdinner.rest.participant.importer.Database
//import de.debuglevel.walkingdinner.rest.plan.Plan
//import de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner.GeneticPlanner
//import de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner.GeneticPlannerOptions
//import de.debuglevel.walkingdinner.rest.plan.report.teams.summary.SummaryReporter
//import io.jenetics.EnumGene
//import io.jenetics.engine.EvolutionResult
//import io.jenetics.engine.EvolutionStatistics
//import io.jenetics.stat.DoubleMomentStatistics
//import mu.KotlinLogging
//import org.apache.commons.validator.routines.UrlValidator
//import java.net.URL
//import java.nio.file.Paths
//import java.util.function.Consumer
//
//class Cli : CliktCommand() {
//    private val logger = KotlinLogging.logger {}
//
//    private val csvFilename by option(help = "URL or file name of CSV file")
//
//    override fun run() {
//        println("=== ${BuildVersion.buildTitle} ${BuildVersion.buildVersion} ===")
//
//        val evolutionStatistics = EvolutionStatistics.ofNumber<Double>()
//        val consumers: Consumer<EvolutionResult<EnumGene<Team>, Double>>? = Consumer {
//            evolutionStatistics.accept(it)
//            printIntermediary(it)
//        }
//
//        val database = buildDatabase()
//
//        val options = GeneticPlannerOptions(
//            evolutionResultConsumer = consumers,
//            teams = database.teams
//        )
//
//        val result = GeneticPlanner(options).plan()
//
//        //processResults(result, evolutionStatistics)
//        processResults(result, evolutionStatistics)
//    }
//
//    private fun processResults(
//        result: Plan,
//        evolutionStatistics: EvolutionStatistics<Double, DoubleMomentStatistics>?
//    ) {
//        println()
//        println(evolutionStatistics)
//
//        println()
//        println(result.additionalInformation)
//
//        SummaryReporter().generateReports(result.meetings)
//        //GmailDraftReporter().generateReports(result.meetings)
//    }
//
////    private fun processResults(result: EvolutionResult<EnumGene<Team>, Double>, evolutionStatistics: EvolutionStatistics<Double, DoubleMomentStatistics>?) {
////        println()
////        println("Best in Generation: " + result.generation)
////        println("Best with Fitness: " + result.bestFitness)
////
////        println()
////        println(evolutionStatistics)
////
////        println()
////        val courses = CoursesProblem(result.bestPhenotype.genotype.gene.validAlleles)
////                .codec()
////                .decode(result.bestPhenotype.genotype)
////        val meetings = courses.toMeetings()
////
////        SummaryReporter().generateReports(meetings)
////        GmailDraftReporter().generateReports(meetings)
////    }
//
//    private fun buildDatabase(): Database {
//        val csvFilename: String = this.csvFilename ?: "Teams_aufbereitet.csv"
//        val csvUrl = when {
//            UrlValidator().isValid(csvFilename) -> URL(csvFilename)
//            else -> Paths.get(csvFilename).toUri().toURL()
//        }
//        val csv = csvUrl.readText()
//
//        val database = Database(csv, "Bamberg, Germany")
//
//        return database
//    }
//
//    private fun printIntermediary(e: EvolutionResult<EnumGene<Team>, Double>) {
//        TimeMeasurement.add("evolveDuration", e.durations.evolveDuration.toNanos(), 500)
//        if (e.generation % 500 == 0L) {
//            logger.trace("${Math.round(1 / (e.durations.evolveDuration.toNanos() / 1_000_000_000.0))}gen/s\t| Generation: ${e.generation}\t| Best Fitness: ${e.bestFitness}")
//        }
//    }
//}
//
//fun main(args: Array<String>) = Cli().main(args)

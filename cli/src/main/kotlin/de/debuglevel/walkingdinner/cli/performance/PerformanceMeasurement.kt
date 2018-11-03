package de.debuglevel.walkingdinner.cli.performance

import de.debuglevel.walkingdinner.geneticplanner.GeneticPlanner
import de.debuglevel.walkingdinner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.importer.Database
import de.debuglevel.walkingdinner.model.BuildVersion
import java.nio.file.Paths

fun main(args: Array<String>) {
    println("=== ${BuildVersion.buildTitle} ${BuildVersion.buildVersion} ===")

    while (true) {
        val csvUrl = Paths.get("Teams_aufbereitet.csv").toUri().toURL()

        val database = Database(csvUrl)
        database.initialize()

        val options = GeneticPlannerOptions(
                evolutionResultConsumer = null,
                database = database
        )

        val result = GeneticPlanner(options).run()
        println("Ended in Generation ${result.bestPhenotype.generation} with Fitness ${result.bestFitness}")
    }
}
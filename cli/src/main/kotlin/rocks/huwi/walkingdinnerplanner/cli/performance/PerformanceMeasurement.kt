package rocks.huwi.walkingdinnerplanner.cli.performance

import rocks.huwi.walkingdinnerplanner.geneticplanner.GeneticPlanner
import rocks.huwi.walkingdinnerplanner.geneticplanner.GeneticPlannerOptions
import rocks.huwi.walkingdinnerplanner.importer.Database
import rocks.huwi.walkingdinnerplanner.model.BuildVersion
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
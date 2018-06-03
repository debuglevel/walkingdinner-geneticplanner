package rocks.huwi.walkingdinnerplanner.cli.performance

import rocks.huwi.walkingdinnerplanner.geneticplanner.GeneticPlanner
import rocks.huwi.walkingdinnerplanner.model.BuildVersion
import java.nio.file.Paths

fun main(args: Array<String>) {
    println("=== ${BuildVersion.buildTitle} ${BuildVersion.buildVersion} ===")

    while (true) {
        val csvUrl = Paths.get("Teams_aufbereitet.csv").toUri().toURL()
        val result = GeneticPlanner(csvUrl, null).run()
        println("Ended in Generation ${result.bestPhenotype.generation} with Fitness ${result.bestFitness}")
    }
}
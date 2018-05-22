package rocks.huwi.walkingdinner.geneticplanner.performance

import rocks.huwi.walkingdinner.geneticplanner.BuildVersion
import rocks.huwi.walkingdinner.geneticplanner.GeneticPlanner
import java.nio.file.Paths

fun main(args: Array<String>) {
    println("=== ${BuildVersion.buildTitle} ${BuildVersion.buildVersion} ===")

    while (true) {
        val csvUrl = Paths.get("Teams_aufbereitet.csv").toUri().toURL()
        val result = GeneticPlanner(csvUrl, null).run()
        println("Ended in Generation ${result.bestPhenotype.generation} with Fitness ${result.bestFitness}")
    }
}
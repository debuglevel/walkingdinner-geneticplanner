package rocks.huwi.walkingdinnerplanner.geneticplanner.performance

import rocks.huwi.walkingdinnerplanner.geneticplanner.BuildVersion
import rocks.huwi.walkingdinnerplanner.geneticplanner.GeneticPlanner
import java.nio.file.Paths

fun main(args: Array<String>) {
    println("=== ${rocks.huwi.walkingdinnerplanner.geneticplanner.BuildVersion.buildTitle} ${rocks.huwi.walkingdinnerplanner.geneticplanner.BuildVersion.buildVersion} ===")

    while (true) {
        val csvUrl = Paths.get("Teams_aufbereitet.csv").toUri().toURL()
        val result = rocks.huwi.walkingdinnerplanner.geneticplanner.GeneticPlanner(csvUrl, null).run()
        println("Ended in Generation ${result.bestPhenotype.generation} with Fitness ${result.bestFitness}")
    }
}
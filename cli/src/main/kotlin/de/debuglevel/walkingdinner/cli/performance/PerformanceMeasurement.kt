package de.debuglevel.walkingdinner.cli.performance

import de.debuglevel.walkingdinner.importer.Database
import de.debuglevel.walkingdinner.model.BuildVersion
import de.debuglevel.walkingdinner.planner.geneticplanner.GeneticPlanner
import de.debuglevel.walkingdinner.planner.geneticplanner.GeneticPlannerOptions
import java.nio.file.Paths

fun main(args: Array<String>) {
    println("=== ${BuildVersion.buildTitle} ${BuildVersion.buildVersion} ===")

    while (true) {
        val csvUrl = Paths.get("Teams_aufbereitet.csv").toUri().toURL()

        val database = Database(csvUrl, "Bamberg, Germany")

        val options = GeneticPlannerOptions(
                evolutionResultConsumer = null,
                teams = database.teams
        )

        val result = GeneticPlanner(options).plan()
        println(result.additionalInformation)
    }
}
package rocks.huwi.walkingdinnerplanner.geneticplanner.imports

import rocks.huwi.walkingdinnerplanner.model.location.locator.DatabasecacheGeolocator
import rocks.huwi.walkingdinnerplanner.model.team.Team
import java.net.URL


class Database(private val csvFile: URL) {
    val teams = mutableListOf<Team>()

    fun print() {
        println("Teams in database:")
        teams.forEach { println(it) }
        println()
    }

    fun initialize() {
        println("Initializing database...")
        initializeTeams()
    }

    private fun initializeTeams() {
        println("Initializing teams...")
        this.teams.addAll(CsvTeamImporter(csvFile).import())
        initializeTeamLocations()
    }

    private fun initializeTeamLocations() {
        println("Fetching geo-information for teams...")
        val databasecacheGeolocator = DatabasecacheGeolocator("Bamberg, Germany")
        this.teams.parallelStream()
                .forEach { databasecacheGeolocator.initializeTeamLocation(it) }
    }
}
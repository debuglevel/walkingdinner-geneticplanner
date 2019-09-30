package de.debuglevel.walkingdinner.rest.participant.importer

import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.importer.csv.CsvTeamImporter
import de.debuglevel.walkingdinner.rest.participant.location.locator.DatabasecacheGeolocator
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class DatabaseBuilder(private val databasecacheGeolocator: DatabasecacheGeolocator) {
    private val logger = KotlinLogging.logger {}

    fun build(csv: String): Database {
        logger.debug { "Building database..." }

        val teams = initializeTeams(csv)
        val database = Database(teams)

        logger.debug { "Built database" }
        return database
    }

    private fun initializeTeams(csv: String): List<Team> {
        logger.debug { "Initializing teams..." }

        val teams = CsvTeamImporter(csv).import()
        initializeTeamLocations(teams)

        logger.debug { "Initialized teams" }
        return teams
    }

    private fun initializeTeamLocations(teams: List<Team>) {
        logger.debug { "Initializing team locations..." }

        teams.parallelStream()
            .forEach { databasecacheGeolocator.initializeTeamLocation(it) }

        logger.debug { "Initialized team locations" }
    }
}
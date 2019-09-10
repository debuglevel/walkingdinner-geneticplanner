package de.debuglevel.walkingdinner.importer

import de.debuglevel.walkingdinner.importer.csv.CsvTeamImporter
import de.debuglevel.walkingdinner.model.location.locator.DatabasecacheGeolocator
import de.debuglevel.walkingdinner.rest.participant.Team
import mu.KotlinLogging

class Database {
    private val logger = KotlinLogging.logger {}

    val teams = mutableListOf<Team>()

    constructor(csv: String, location: String) {
        initialize(csv, location)
    }

    private fun initialize(csv: String, location: String) {
        logger.debug("Initializing database...")
        initializeTeams(csv, location)
    }

    private fun initializeTeams(csv: String, location: String) {
        logger.debug("Initializing teams...")
        this.teams.addAll(CsvTeamImporter(csv).import())
        initializeTeamLocations(location)
    }

    private fun initializeTeamLocations(location: String) {
        logger.debug("Initializing team locations...")
        val databasecacheGeolocator = DatabasecacheGeolocator(location)
        this.teams.parallelStream()
            .forEach { databasecacheGeolocator.initializeTeamLocation(it) }
    }
}
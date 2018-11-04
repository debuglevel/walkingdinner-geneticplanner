package de.debuglevel.walkingdinner.importer

import de.debuglevel.walkingdinner.model.location.locator.DatabasecacheGeolocator
import de.debuglevel.walkingdinner.model.team.Team
import mu.KotlinLogging
import java.net.URL


class Database(private val csvFile: URL) {
    private val logger = KotlinLogging.logger {}

    val teams = mutableListOf<Team>()

    fun print() {
        logger.debug("Teams in database:")
        teams.forEach { logger.debug { it } }
    }

    fun initialize() {
        logger.debug("Initializing database...")
        initializeTeams()
    }

    private fun initializeTeams() {
        logger.debug("Initializing teams...")
        this.teams.addAll(CsvTeamImporter(csvFile).import())
        initializeTeamLocations()
    }

    private fun initializeTeamLocations() {
        logger.debug("Fetching geo-information for teams...")
        val databasecacheGeolocator = DatabasecacheGeolocator("Bamberg, Germany")
        this.teams.parallelStream()
                .forEach { databasecacheGeolocator.initializeTeamLocation(it) }
    }
}
package de.debuglevel.walkingdinner.importer

import de.debuglevel.walkingdinner.importer.csv.CsvTeamImporter
import de.debuglevel.walkingdinner.model.location.locator.DatabasecacheGeolocator
import de.debuglevel.walkingdinner.model.team.Team
import mu.KotlinLogging
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths

class Database {
    private val logger = KotlinLogging.logger {}

    val teams = mutableListOf<Team>()

    constructor(csvPath: Path, location: String) {
        val csvFilename = csvPath.toAbsolutePath().toString()
        val csvUrl = Paths.get(csvFilename).toUri().toURL()

        initialize(csvUrl, location)
    }

    constructor(csvUrl: URL, location: String) {
        initialize(csvUrl, location)
    }

    private fun initialize(csvUrl: URL, location: String) {
        logger.debug("Initializing database...")
        initializeTeams(csvUrl, location)
    }

    private fun initializeTeams(csvUrl: URL, location: String) {
        logger.debug("Initializing teams...")
        this.teams.addAll(CsvTeamImporter(csvUrl).import())
        initializeTeamLocations(location)
    }

    private fun initializeTeamLocations(location: String) {
        logger.debug("Initializing team locations...")
        val databasecacheGeolocator = DatabasecacheGeolocator(location)
        this.teams.parallelStream()
                .forEach { databasecacheGeolocator.initializeTeamLocation(it) }
    }
}
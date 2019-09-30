package de.debuglevel.walkingdinner.rest.participant.location.locator

import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.location.GeoUtils
import de.debuglevel.walkingdinner.rest.participant.location.Location
import io.jsondb.JsonDBTemplate
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Paths
import java.text.DecimalFormat
import javax.inject.Singleton

@Singleton
class DatabasecacheGeolocator(
    private val nominatimApiGeolocator: NominatimApiGeolocator
) : Geolocator {
    private val logger = KotlinLogging.logger {}

    private val fallbackGeolocator: Geolocator

    private lateinit var jsonDBTemplate: JsonDBTemplate
    private val locations = mutableListOf<Location>()

    init {
        logger.debug { "Initializing DatabasecacheGeolocator..." }

        //this.fallbackGeolocator = GoogleApiGeolocator(this.city)
        this.fallbackGeolocator = nominatimApiGeolocator

        initializeJsondb()

        logger.debug { "Initialized DatabasecacheGeolocator" }
    }

    private fun initializeJsondb() {
        logger.debug { "Initializing JsonDB..." }

        val databasePath = Paths.get("database")
        if (!Files.exists(databasePath)) {
            Files.createDirectory(databasePath)
        }

        val dbFilesLocation = databasePath.toString()
        val baseScanPackage = "de.debuglevel.walkingdinner"

        jsonDBTemplate = JsonDBTemplate(dbFilesLocation, baseScanPackage)

        if (!jsonDBTemplate.collectionExists(Location::class.java)) {
            jsonDBTemplate.createCollection(Location::class.java)
        }

        locations.addAll(jsonDBTemplate.findAll(Location::class.java))

        logger.debug { "Initialized JsonDB" }
    }

    override fun getLocation(address: String?, city: String): Location {
        logger.debug { "Getting location for '$address' ..." }

        var location = locations.firstOrNull { it.address == address }

        if (location == null) {
            logger.debug("Location $address not found in caching database. Using fallback Geolocator...")
            location = this.fallbackGeolocator.getLocation(address, city)

            addLocation(location)
        } else {
            logger.debug("Got location for $address (found in caching database)")
        }

        return location
    }

    private fun addLocation(location: Location) {
        logger.debug("Adding Location $location to caching database...")
        jsonDBTemplate.insert<Location>(location)
        locations.add(location)
        logger.debug("Added Location $location to caching database")
    }

    override fun initializeTeamLocation(team: Team) {
        logger.debug("Geo-locating $team by caching database...")
        val location = getLocation(team.address, team.city)
        team.location = location

        val cityLocation = getLocation(null, team.city)

        val centerDistance = GeoUtils.calculateDistanceInKilometer(cityLocation, location)
        logger.debug("Geo-located $team ${DecimalFormat("#.##").format(centerDistance)}km from town center by caching database")
    }
}
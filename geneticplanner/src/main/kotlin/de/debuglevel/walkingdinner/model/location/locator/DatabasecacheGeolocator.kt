package de.debuglevel.walkingdinner.model.location.locator

import io.jsondb.JsonDBTemplate
import de.debuglevel.walkingdinner.model.location.GeoUtils
import de.debuglevel.walkingdinner.model.location.Location
import de.debuglevel.walkingdinner.model.team.Team
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Paths
import java.text.DecimalFormat


class DatabasecacheGeolocator(private val city: String) : Geolocator {
    private val logger = KotlinLogging.logger {}

    private val fallbackGeolocator: Geolocator

    private lateinit var jsonDBTemplate: JsonDBTemplate
    private val locations = mutableListOf<Location>()
    private val cityLocation: Location

    init {
        logger.debug { "Initializing DatabasecacheGeolocator..." }

        //this.fallbackGeolocator = GoogleApiGeolocator(this.city)
        this.fallbackGeolocator = NominatimApiGeolocator(this.city)

        initializeJsondb()

        cityLocation = getLocation("")
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
    }

    override fun getLocation(address: String): Location {
        logger.debug { "Getting location for '$address' ..." }

        var location = locations.firstOrNull { it.address == address }

        if (location == null) {
            logger.debug("Location $address not found in caching database. Using fallback Geolocator...")
            location = this.fallbackGeolocator.getLocation(address)

            addLocation(location)
        } else {
            logger.debug("Location $address found in caching database.")
        }

        return location
    }

    private fun addLocation(location: Location) {
        logger.debug("Adding Location $location to caching database.")
        jsonDBTemplate.insert<Location>(location)
        locations.add(location)
    }

    override fun initializeTeamLocation(team: Team) {
        logger.debug("Geo-locating $team by caching database...")
        val location = getLocation(team.address)
        team.location = location

        val distanceToCity = GeoUtils.calculateDistanceInKilometer(cityLocation, location)
        logger.debug("Geo-located $team ${DecimalFormat("#.##").format(distanceToCity)}km from center by caching database")
    }
}
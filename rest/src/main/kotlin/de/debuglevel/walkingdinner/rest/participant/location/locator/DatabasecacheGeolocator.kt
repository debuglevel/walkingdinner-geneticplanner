package de.debuglevel.walkingdinner.rest.participant.location.locator

import de.debuglevel.walkingdinner.rest.common.GeoUtils
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.location.Location
import io.jsondb.JsonDBTemplate
import io.micronaut.context.annotation.Property
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Path
import java.text.DecimalFormat
import javax.inject.Singleton

@Singleton
class DatabasecacheGeolocator(
    private val nominatimApiGeolocator: NominatimApiGeolocator,
    @Property(name = "app.walkingdinner.data.base-path") private val dataBasepath: Path
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

        val databasePath = dataBasepath.resolve("database-cache-geolocator")
        logger.debug { "Using path '$databasePath'..." }
        if (!Files.exists(databasePath)) {
            logger.debug { "Creating directory '$databasePath' as it does not exist..." }
            Files.createDirectories(databasePath)
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
        logger.debug { "Getting location for '$address' in city '$city' ..." }

        // HACK: somehow handle the "address, city" concatenation and city lookup
        //locations.forEach { logger.debug { "== '$it'" } }
        var location = if (address != null) {
            locations.firstOrNull { it.address == "$address, $city" }
        } else {
            locations.firstOrNull { it.address == city }
        }

        if (location == null) {
            logger.debug("Location $address not found in caching database. Using fallback Geolocator...")
            location = this.fallbackGeolocator.getLocation(address, city)

            addLocation(location)
        } else {
            logger.debug("Got location for '$address' in city '$city' (found in caching database): $location")
        }

        //return location
        return location.copy() // kind of HACK: create a new object, as this otherwise interferes with already existing and persistent database objects
    }

    private fun addLocation(location: Location) {
        logger.debug("Adding Location $location to caching database...")
        jsonDBTemplate.insert<Location>(location)
        locations.add(location)
        logger.debug("Added Location $location to caching database")
    }

    override fun initializeTeamLocation(team: Team) {
        logger.debug("Geo-locating $team (city '${team.city}', address '${team.address}') by caching database...")
        val location = getLocation(team.address, team.city)
        team.location = location

        val cityLocation = getLocation(null, team.city)

        val centerDistance = GeoUtils.calculateDistanceInKilometer(cityLocation, location)
        logger.debug("Geo-located $team ${DecimalFormat("#.##").format(centerDistance)}km from town center by caching database")
    }
}
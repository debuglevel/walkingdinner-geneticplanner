package rocks.huwi.walkingdinnerplanner.model.location.locator

import io.jsondb.JsonDBTemplate
import rocks.huwi.walkingdinnerplanner.model.location.GeoUtils
import rocks.huwi.walkingdinnerplanner.model.location.Location
import rocks.huwi.walkingdinnerplanner.model.team.Team
import java.nio.file.Files
import java.nio.file.Paths
import java.text.DecimalFormat


class DatabasecacheGeolocator(private val city: String) : Geolocator {

    private val fallbackGeolocator: Geolocator

    private lateinit var jsonDBTemplate: JsonDBTemplate
    private val locations = mutableListOf<Location>()
    private val cityLocation: Location

    init {
        this.fallbackGeolocator = GoogleApiGeolocator(this.city)

        initializeJsondb()

        cityLocation = getLocation(this.city)
    }

    private fun initializeJsondb() {
        val databasePath = Paths.get("database")
        if (!Files.exists(databasePath)) {
            Files.createDirectory(databasePath)
        }

        val dbFilesLocation = databasePath.toString()
        val baseScanPackage = "rocks.huwi.walkingdinnerplanner"

        jsonDBTemplate = JsonDBTemplate(dbFilesLocation, baseScanPackage)

        if (!jsonDBTemplate.collectionExists(Location::class.java)) {
            jsonDBTemplate.createCollection(Location::class.java)
        }

        locations.addAll(jsonDBTemplate.findAll(Location::class.java))
    }

    override fun getLocation(address: String): Location {
        var location = locations.firstOrNull { it.address == address }

        if (location == null) {
            println("Location $address not found in caching database. Using fallback Geolocator...")
            location = this.fallbackGeolocator.getLocation(address)

            addLocation(location)
        } else {
            println("Location $address found in caching database.")
        }

        return location
    }

    private fun addLocation(location: Location) {
        println("Adding Location $location to caching database.")
        jsonDBTemplate.insert<Location>(location)
        locations.add(location)
    }

    override fun initializeTeamLocation(team: Team) {
        println("Geo-locating $team by caching database...")
        val location = getLocation(team.address)
        team.location = location

        val distanceToCity = GeoUtils.calculateDistanceInKilometer(cityLocation, location)
        println("Geo-located $team ${DecimalFormat("#.##").format(distanceToCity)}km from center by caching database")
    }
}
package de.debuglevel.walkingdinner.model.location.locator

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import de.debuglevel.walkingdinner.model.location.GeoUtils
import de.debuglevel.walkingdinner.model.location.Location
import de.debuglevel.walkingdinner.model.team.Team
import java.text.DecimalFormat

class GoogleApiGeolocator(private val city: String) : Geolocator {
    private val geoCodingApi = GeoApiContext.Builder()
            .apiKey("AIzaSyB9eOPva1kx-_p4R7taF42DoF2ZdRNsuZs")
            .build()

    private val cityLocation: Location

    override fun getLocation(address: String): Location {
        val result = GeocodingApi
                .geocode(geoCodingApi, "$address $city")
                .await()
                .first()

        return Location(
                address,
                result.geometry.location.lng,
                result.geometry.location.lat)
    }

    override fun initializeTeamLocation(team: Team) {
        println("Geo-locating $team by Google API...")

        val location = getLocation(team.address)
        team.location = location

        val distanceToCity = GeoUtils.calculateDistanceInKilometer(cityLocation, location)

        println("Geo-located $team ${DecimalFormat("#.##").format(distanceToCity)}km from center by Google API")
    }

    init {
        val result = GeocodingApi
                .geocode(geoCodingApi, city)
                .await()
                .first()
        cityLocation = Location(
                city,
                result.geometry.location.lng,
                result.geometry.location.lat)
    }
}
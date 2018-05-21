package rocks.huwi.walkingdinner.geneticplanner

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import java.text.DecimalFormat

class GoogleApiGeolocator(private val city: String) : Geolocator {
    private val geoCodingApi = GeoApiContext.Builder()
            .apiKey("AIzaSyChROm89xSBYdbVenzTr1F3r0MUEhBX6Xc")
            .build()

    private val cityLocation: Location

    override fun getLocation(address: String): Location {
        val result = GeocodingApi
                .geocode(geoCodingApi, "$address $city")
                .await()
                .first()

        val location = Location(
                address,
                result.geometry.location.lng,
                result.geometry.location.lat)

        return location
    }

    override fun initializeTeamLocation(team: Team) {
        println("Geo-locating $team by Google API...")

        team.location = getLocation(team.address)

        val distanceToCity = GeoUtils.calculateDistanceInKilometer(cityLocation, team.location)

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
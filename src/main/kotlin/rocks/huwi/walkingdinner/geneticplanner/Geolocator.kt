package rocks.huwi.walkingdinner.geneticplanner

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import java.text.DecimalFormat

class Geolocator(private val city: String) {
    private val geoCodingApi = GeoApiContext.Builder()
            .apiKey("AIzaSyChROm89xSBYdbVenzTr1F3r0MUEhBX6Xc")
            .build()

    private val cityLocation: Location

    fun initializeTeamLocation(team: Team) {
        println("Geo-locating $team...")

        val result = GeocodingApi
                .geocode(geoCodingApi, "${team.address} $city")
                .await()
                .first()

        team.location = Location(
                result.geometry.location.lng,
                result.geometry.location.lat)

        val distanceToCity = cityLocation.calculateDistance(team.location)

        println("Geo-located $team ${DecimalFormat("#.##").format(distanceToCity)}km from center")
    }

    init {
        val result = GeocodingApi
                .geocode(geoCodingApi, city)
                .await()
                .first()
        cityLocation = Location(
                result.geometry.location.lng,
                result.geometry.location.lat)
    }
}
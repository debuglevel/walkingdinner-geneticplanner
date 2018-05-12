package rocks.huwi.walkingdinner.geneticplanner

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi

class Geolocator {
    companion object {
        val context = GeoApiContext.Builder()
                .apiKey("AIzaSyChROm89xSBYdbVenzTr1F3r0MUEhBX6Xc")
                .build()

        val city = "Bamberg, Germany"

        fun initializeLocation(team: Team) {
            val results = GeocodingApi
                    .geocode(context, "${team.address} $city").await()

            team.location = Location(
                    results.first().geometry.location.lng,
                    results.first().geometry.location.lat)
        }
    }
}
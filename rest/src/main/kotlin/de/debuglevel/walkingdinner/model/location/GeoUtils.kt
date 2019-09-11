package de.debuglevel.walkingdinner.model.location

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object GeoUtils {
    private val distances = hashMapOf<Pair<Location, Location>, Double>()

    private const val AVERAGE_RADIUS_OF_EARTH_KM = 6371.0
    fun calculateDistanceInKilometer(
        source: Location,
        destination: Location
    ): Double {

        val pair = Pair(source, destination)
        var distance = distances[pair]
        return if (distance != null) {
            distance
        } else {
            val latDistance = Math.toRadians(source.lat - destination.lat)
            val lngDistance = Math.toRadians(source.lng - destination.lng)

            val a =
                sin(latDistance / 2) * sin(latDistance / 2) + (cos(Math.toRadians(source.lat)) * cos(
                    Math.toRadians(destination.lat)
                )
                        * sin(lngDistance / 2) * sin(lngDistance / 2))

            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            distance = AVERAGE_RADIUS_OF_EARTH_KM * c
            distances[pair] = distance
            distance
        }
    }
}
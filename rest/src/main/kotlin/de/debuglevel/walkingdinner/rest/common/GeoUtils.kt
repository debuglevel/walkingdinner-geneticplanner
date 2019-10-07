package de.debuglevel.walkingdinner.rest.common

import de.debuglevel.walkingdinner.rest.participant.location.Location
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object GeoUtils {
    /**
     * Cache for already calculated distances
     *
     * @remarks Microbenchmark: w/ cache 5000ms for 100.000.000 requests, w/o cache 8000ms.
     */
    private val distances = hashMapOf<Pair<Location, Location>, Double>()

    private const val AVERAGE_RADIUS_OF_EARTH_KM = 6371.0

    fun calculateDistanceInKilometer(
        source: Location,
        destination: Location
    ): Double {
        // Retrieve distance from cache if already calculated
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

            // Put calculation into cache
            distances[pair] = distance

            distance
        }
    }
}
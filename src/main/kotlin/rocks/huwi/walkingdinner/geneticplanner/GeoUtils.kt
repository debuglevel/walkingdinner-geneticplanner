package rocks.huwi.walkingdinner.geneticplanner

object GeoUtils {
    private val distances = mutableMapOf<GeoPair, Double>()

    private const val AVERAGE_RADIUS_OF_EARTH_KM = 6371.0
    fun calculateDistanceInKilometer(sourceLat: Double, sourceLng: Double,
                                     destinationLat: Double, destinationLng: Double): Double {

        val pair = GeoPair(sourceLat, sourceLng, destinationLat, destinationLng)
        var distance = distances[pair]
        if (distance != null)
        {
            return distance
        }else{
            val latDistance = Math.toRadians(sourceLat - destinationLat)
            val lngDistance = Math.toRadians(sourceLng - destinationLng)

            val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + (Math.cos(Math.toRadians(sourceLat)) * Math.cos(Math.toRadians(destinationLat))
                    * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2))

            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

            distance = AVERAGE_RADIUS_OF_EARTH_KM * c
            distances[pair] = distance
            return distance
        }
    }

    data class GeoPair(val sourceLat: Double, val sourceLng: Double,
                        val destinationLat: Double, val destinationLng: Double) {
    }
}
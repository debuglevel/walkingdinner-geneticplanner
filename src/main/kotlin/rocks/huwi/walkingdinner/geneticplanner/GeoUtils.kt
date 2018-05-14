package rocks.huwi.walkingdinner.geneticplanner

object GeoUtils {
    private const val AVERAGE_RADIUS_OF_EARTH_KM = 6371.0
    fun calculateDistanceInKilometer(sourceLat: Double, sourceLng: Double,
                                     destinationLat: Double, destinationLng: Double): Double {

        val latDistance = Math.toRadians(sourceLat - destinationLat)
        val lngDistance = Math.toRadians(sourceLng - destinationLng)

        val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + (Math.cos(Math.toRadians(sourceLat)) * Math.cos(Math.toRadians(destinationLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2))

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return AVERAGE_RADIUS_OF_EARTH_KM * c
    }
}
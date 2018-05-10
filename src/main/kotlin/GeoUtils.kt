class GeoUtils {
    companion object {
        private val AVERAGE_RADIUS_OF_EARTH_KM = 6371.0
        fun calculateDistanceInKilometer(sourceLat: Double, sourceLng: Double,
                                         destionationLat: Double, destionationLng: Double): Double {

            val latDistance = Math.toRadians(sourceLat - destionationLat)
            val lngDistance = Math.toRadians(sourceLng - destionationLng)

            val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + (Math.cos(Math.toRadians(sourceLat)) * Math.cos(Math.toRadians(destionationLat))
                    * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2))

            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

            return AVERAGE_RADIUS_OF_EARTH_KM * c
        }
    }
}
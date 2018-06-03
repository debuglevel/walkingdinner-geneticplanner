package rocks.huwi.walkingdinnerplanner.model.location

object GeoUtils {
    private val distances = hashMapOf<Pair<Location, Location>, Double>()

    private const val AVERAGE_RADIUS_OF_EARTH_KM = 6371.0
    fun calculateDistanceInKilometer(source: Location,
                                     destination: Location): Double {

        val pair = Pair(source, destination)
        var distance = distances[pair]
        return if (distance != null) {
            distance
        } else {
            val latDistance = Math.toRadians(source.lat - destination.lat)
            val lngDistance = Math.toRadians(source.lng - destination.lng)

            val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + (Math.cos(Math.toRadians(source.lat)) * Math.cos(Math.toRadians(destination.lat))
                    * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2))

            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

            distance = AVERAGE_RADIUS_OF_EARTH_KM * c
            distances[pair] = distance
            distance
        }
    }
}
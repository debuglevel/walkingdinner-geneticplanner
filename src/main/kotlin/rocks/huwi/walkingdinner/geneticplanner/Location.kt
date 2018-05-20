package rocks.huwi.walkingdinner.geneticplanner

data class Location(val lng: Double, val lat: Double) {
    fun calculateDistance(location: Location): Double = GeoUtils.calculateDistanceInKilometer(this, location)
}
package rocks.huwi.walkingdinner.geneticplanner

data class Location(private val lng: Double, private val lat: Double) {
    fun calculateDistance(lng: Double, lat: Double) = GeoUtils.calculateDistanceInKilometer(this.lat, this.lng, lat, lng)

    fun calculateDistance(location: Location) = GeoUtils.calculateDistanceInKilometer(this.lat, this.lng, location.lat, location.lng)
}
data class Location(val lng: Double, val lat: Double) {
    fun calculateDistance(lng: Double, lat: Double) = GeoUtils.calculateDistanceInKilometer(this.lat, this.lng, lat, lng)

    fun calculateDistance(location: Location) = GeoUtils.calculateDistanceInKilometer(this.lat, this.lng, location.lat, location.lng)
}
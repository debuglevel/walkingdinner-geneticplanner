package de.debuglevel.walkingdinner.planners

import de.debuglevel.walkingdinner.planners.common.GeoUtils

class Location(
    val lng: Double,
    val lat: Double
) {
    fun calculateDistance(location: Location): Double {
        return GeoUtils.calculateDistanceInKilometer(this, location)
    }
}

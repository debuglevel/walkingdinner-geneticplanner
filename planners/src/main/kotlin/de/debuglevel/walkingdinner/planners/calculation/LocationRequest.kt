package de.debuglevel.walkingdinner.planners.calculation

import de.debuglevel.walkingdinner.planners.Location

class LocationRequest(
    val lng: Double,
    val lat: Double
) {
    fun toLocation(): Location {
        return Location(lng, lat)
    }
}

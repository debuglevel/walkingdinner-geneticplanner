package rocks.huwi.walkingdinnerplanner.model.location.locator

import rocks.huwi.walkingdinnerplanner.model.location.Location
import rocks.huwi.walkingdinnerplanner.model.team.Team

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String): Location
}

package rocks.huwi.walkingdinnerplanner.geneticplanner.location.locator

import rocks.huwi.walkingdinnerplanner.geneticplanner.location.Location
import rocks.huwi.walkingdinnerplanner.geneticplanner.team.Team

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String): Location
}

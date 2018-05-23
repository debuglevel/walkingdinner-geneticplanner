package rocks.huwi.walkingdinner.geneticplanner.location

import rocks.huwi.walkingdinner.geneticplanner.team.Team

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String): Location
}

package rocks.huwi.walkingdinner.geneticplanner

interface Geolocator {
    fun initializeTeamLocation(team: Team)
    fun getLocation(address: String): Location
}

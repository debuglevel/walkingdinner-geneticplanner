package de.debuglevel.walkingdinner.planners

data class Meeting(
    val course: String,
    val teams: List<Team>
) {
    /**
     * Get the team which is cooking in this meeting
     * @implNote The cooking team is defined as the first one in the list
     */
    fun getCookingTeam(): Team {
        return teams.first()
    }
}

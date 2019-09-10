package de.debuglevel.walkingdinner.rest

import de.debuglevel.walkingdinner.rest.participant.Team
import java.util.stream.Collectors

data class Meeting(
    val teams: List<Team>,
    val course: String
) {

    fun getCookingTeam(): Team = teams.first()

    private fun isCookingTeam(team: Team): Boolean = this.getCookingTeam() == team

    override fun toString(): String {
        return teams.stream()
            .map { it -> if (isCookingTeam(it)) "[$it]" else "$it" }
            .collect(Collectors.joining("\t"))
    }
}
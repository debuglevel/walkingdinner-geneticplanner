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
            .map { it ->
                val teamText = "${it.cook1} & ${it.cook2}"
                if (isCookingTeam(it)) "[$teamText]" else teamText
            }
            .collect(Collectors.joining("\t"))
    }
}
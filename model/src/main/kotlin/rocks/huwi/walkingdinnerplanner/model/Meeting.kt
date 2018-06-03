package rocks.huwi.walkingdinnerplanner.model

import rocks.huwi.walkingdinnerplanner.model.team.Team
import java.util.stream.Collectors

data class Meeting(val teams: List<Team>, val course: String) {
    fun getCookingTeam(): Team = teams.first()

    private fun isCook(team: Team): Boolean = this.getCookingTeam() == team

    override fun toString(): String {
        return teams.stream()
                .map { it -> if (isCook(it)) "[$it]" else it.toString() }
                .collect(Collectors.joining("\t"))
    }
}
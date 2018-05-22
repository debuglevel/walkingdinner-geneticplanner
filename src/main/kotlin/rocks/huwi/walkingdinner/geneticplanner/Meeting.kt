package rocks.huwi.walkingdinner.geneticplanner

import java.util.*
import java.util.stream.Collectors

data class Meeting(val teams: Array<Team>, val course: String) {
    fun getCookingTeam(): Team = teams.first()

    private fun isCook(team: Team): Boolean = this.getCookingTeam() == team

    override fun toString(): String {
        return Arrays.stream(teams)
                .map { it -> if (isCook(it)) "[$it]" else it.toString() }
                .collect(Collectors.joining("\t"))
    }
}
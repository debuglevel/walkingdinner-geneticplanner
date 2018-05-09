import java.util.*
import java.util.stream.Collectors

data class Meeting(val teams: Array<Team>, val course: String) {

    fun getCookingTeam(): Team = teams.first()

    fun areCompatibleTeams(): Boolean {
        return teams.all { it.isCompatibleDiet(teams.first()) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Meeting

        if (!Arrays.equals(teams, other.teams)) return false
        if (course != other.course) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(teams)
        result = 31 * result + course.hashCode()
        return result
    }

    fun isCook(team: Team): Boolean
            = this.getCookingTeam() == team

    override fun toString(): String {
        val teams = Arrays.stream(teams)
                .map { it -> if (isCook(it)) "[$it]" else it.toString()}
                .collect(Collectors.joining("\t"))
        return "$teams"
    }
}
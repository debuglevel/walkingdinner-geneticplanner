import java.util.*

data class Meeting(val teams: Array<Team>, val course: String) {

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

    override fun toString(): String {
        return "$course (${Arrays.toString(teams)})"
    }
}
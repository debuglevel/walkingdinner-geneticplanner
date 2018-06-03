package rocks.huwi.walkingdinnerplanner.geneticplanner.dietcompatibility

import rocks.huwi.walkingdinnerplanner.geneticplanner.Meeting
import rocks.huwi.walkingdinnerplanner.geneticplanner.team.Team

object HardCompatibility : Compatibility {
    override fun areCompatibleTeams(meeting: Meeting): Boolean {
        return meeting.teams.all { isCompatibleDiet(meeting.teams.first(), it) }
    }

    fun isCompatibleDiet(a: Team, b: Team) = a.diet == b.diet
}
package rocks.huwi.walkingdinnerplanner.model.dietcompatibility

import rocks.huwi.walkingdinnerplanner.model.Meeting
import rocks.huwi.walkingdinnerplanner.model.team.Team

object HardCompatibility : Compatibility {
    override fun areCompatibleTeams(meeting: Meeting): Boolean {
        return meeting.teams.all { isCompatibleDiet(meeting.teams.first(), it) }
    }

    fun isCompatibleDiet(a: Team, b: Team) = a.diet == b.diet
}
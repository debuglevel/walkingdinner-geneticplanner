package rocks.huwi.walkingdinner.geneticplanner.dietcompatibility

import rocks.huwi.walkingdinner.geneticplanner.Meeting
import rocks.huwi.walkingdinner.geneticplanner.team.Team

object HardCompatibility : Compatibility {
    override fun areCompatibleTeams(meeting: Meeting): Boolean {
        return meeting.teams.all { isCompatibleDiet(meeting.teams.first(), it) }
    }

    fun isCompatibleDiet(a: Team, b: Team) = a.diet == b.diet
}
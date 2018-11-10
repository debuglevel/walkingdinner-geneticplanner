package de.debuglevel.walkingdinner.model.dietcompatibility

import de.debuglevel.walkingdinner.model.Meeting
import de.debuglevel.walkingdinner.model.team.Team

object HardCompatibility : Compatibility {
    override fun areCompatibleTeams(meeting: Meeting): Boolean {
        return meeting.teams.all { isCompatibleDiet(meeting.teams.first(), it) }
    }

    fun isCompatibleDiet(a: Team, b: Team) = a.diet == b.diet
}
package de.debuglevel.walkingdinner.planners.dietcompatibility

import de.debuglevel.walkingdinner.planners.Meeting

interface DietCompatibility {
    fun areCompatibleTeams(meeting: Meeting): Boolean
}
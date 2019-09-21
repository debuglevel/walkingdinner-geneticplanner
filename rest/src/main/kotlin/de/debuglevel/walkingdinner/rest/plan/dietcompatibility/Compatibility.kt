package de.debuglevel.walkingdinner.rest.plan.dietcompatibility

import de.debuglevel.walkingdinner.rest.Meeting

interface Compatibility {
    fun areCompatibleTeams(meeting: Meeting): Boolean
}
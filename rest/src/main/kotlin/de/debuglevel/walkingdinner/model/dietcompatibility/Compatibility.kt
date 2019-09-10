package de.debuglevel.walkingdinner.model.dietcompatibility

import de.debuglevel.walkingdinner.rest.Meeting

interface Compatibility {
    fun areCompatibleTeams(meeting: Meeting): Boolean
}
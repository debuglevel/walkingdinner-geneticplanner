package de.debuglevel.walkingdinner.model.dietcompatibility

import de.debuglevel.walkingdinner.model.Meeting

interface Compatibility {
    fun areCompatibleTeams(meeting: Meeting): Boolean
}
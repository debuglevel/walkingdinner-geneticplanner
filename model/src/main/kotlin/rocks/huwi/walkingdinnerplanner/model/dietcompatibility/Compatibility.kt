package rocks.huwi.walkingdinnerplanner.model.dietcompatibility

import rocks.huwi.walkingdinnerplanner.model.Meeting

interface Compatibility {
    fun areCompatibleTeams(meeting: Meeting): Boolean
}
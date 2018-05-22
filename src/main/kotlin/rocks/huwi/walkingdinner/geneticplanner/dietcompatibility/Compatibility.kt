package rocks.huwi.walkingdinner.geneticplanner.dietcompatibility

import rocks.huwi.walkingdinner.geneticplanner.Meeting

interface Compatibility {
    fun areCompatibleTeams(meeting: Meeting): Boolean
}
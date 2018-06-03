package rocks.huwi.walkingdinnerplanner.geneticplanner.dietcompatibility

import rocks.huwi.walkingdinnerplanner.geneticplanner.Meeting

interface Compatibility {
    fun areCompatibleTeams(meeting: Meeting): Boolean
}
package rocks.huwi.walkingdinnerplanner.report

import rocks.huwi.walkingdinnerplanner.geneticplanner.Meeting

interface Reporter {
    fun generateReports(meetings: Set<Meeting>): Any
}
package rocks.huwi.walkingdinnerplanner.report

import rocks.huwi.walkingdinnerplanner.model.Meeting

interface Reporter {
    fun generateReports(meetings: Set<Meeting>): Any
}
package rocks.huwi.walkingdinner.geneticplanner.report

import rocks.huwi.walkingdinner.geneticplanner.Meeting

interface Reporter {
    fun generateReports(meetings: Set<Meeting>): Any
}
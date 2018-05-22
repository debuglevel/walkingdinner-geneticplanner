package rocks.huwi.walkingdinner.geneticplanner.report

import rocks.huwi.walkingdinner.geneticplanner.Meeting

interface Reporter {
    fun generateReport(meetings: Set<Meeting>)
}
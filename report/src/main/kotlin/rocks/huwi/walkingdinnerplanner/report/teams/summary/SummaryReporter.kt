package rocks.huwi.walkingdinnerplanner.report.teams.summary

import rocks.huwi.walkingdinnerplanner.model.Meeting
import rocks.huwi.walkingdinnerplanner.report.Reporter

class SummaryReporter : Reporter {
    override fun generateReports(meetings: Set<Meeting>) {
        meetings
                .groupBy { it.course }
                .forEach { course, meetings ->
                    run {

                        println()
                        println("== Course $course")
                        meetings.forEach { println(it) }
                    }
                }
    }
}
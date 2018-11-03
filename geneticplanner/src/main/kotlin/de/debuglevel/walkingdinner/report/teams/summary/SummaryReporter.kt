package de.debuglevel.walkingdinner.report.teams.summary

import de.debuglevel.walkingdinner.model.Meeting
import de.debuglevel.walkingdinner.report.Reporter

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
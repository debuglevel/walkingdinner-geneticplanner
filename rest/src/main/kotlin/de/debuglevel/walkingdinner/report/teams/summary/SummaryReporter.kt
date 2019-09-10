package de.debuglevel.walkingdinner.report.teams.summary

import de.debuglevel.walkingdinner.report.Reporter
import de.debuglevel.walkingdinner.rest.Meeting
import mu.KotlinLogging

class SummaryReporter : Reporter {
    private val logger = KotlinLogging.logger {}

    override fun generateReports(meetings: Set<Meeting>) {
        meetings
            .groupBy { it.course }
            .forEach { course, meetings_ ->
                run {

                    println()
                    println("== Course $course")
                    meetings_.forEach { println(it) }
                }
            }
    }
}
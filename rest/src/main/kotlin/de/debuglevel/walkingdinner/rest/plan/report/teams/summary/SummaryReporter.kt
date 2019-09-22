package de.debuglevel.walkingdinner.rest.plan.report.teams.summary

import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.plan.report.Reporter
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class SummaryReporter : Reporter {
    private val logger = KotlinLogging.logger {}

    override fun generateReports(meetings: Set<Meeting>) {
        logger.trace { "Generating summary report..." }
        meetings
            .groupBy { it.course }
            .forEach { (course, meetings_) ->
                run {
                    println()
                    println("== Course $course")
                    meetings_.forEach { println(it) }
                }
            }
        logger.trace { "Generated summary report" }
    }
}
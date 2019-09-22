package de.debuglevel.walkingdinner.rest.plan.report.teams.summary

import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.plan.report.Reporter
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class SummaryReporter : Reporter {
    private val logger = KotlinLogging.logger {}

    override fun generateReports(meetings: Set<Meeting>): String {
        logger.trace { "Generating summary report..." }
        val summary = meetings
            .groupBy { it.course }
            .map { (course, meetings_) ->
                var text = "== Course $course\n"
                text += meetings_.joinToString("\n")
                text
            }
            .joinToString("\n\n")

        logger.trace { "Generated summary report" }
        return summary
    }
}
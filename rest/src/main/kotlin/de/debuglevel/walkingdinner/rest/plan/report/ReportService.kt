package de.debuglevel.walkingdinner.rest.plan.report

import de.debuglevel.walkingdinner.rest.plan.PlanService
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.gmail.GmailService
import de.debuglevel.walkingdinner.rest.plan.report.teams.summary.SummaryReporter
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class ReportService(
    private val textReportService: TextReportService,
    private val summaryReporter: SummaryReporter,
    private val gmailService: GmailService,
    private val planService: PlanService
) {
    private val logger = KotlinLogging.logger {}

    fun getSummary(planId: UUID): String {
        val plan = planService.get(planId)
        return summaryReporter.generateReports(plan.meetings)
    }
}
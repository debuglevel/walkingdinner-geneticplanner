package de.debuglevel.walkingdinner.rest.plan.report

import de.debuglevel.walkingdinner.rest.plan.PlanService
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.gmail.GmailDraftReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.summary.SummaryReporter
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class ReportService(
    private val textReportService: TextReportService,
    private val summaryReporter: SummaryReporter,
    private val gmailDraftReportService: GmailDraftReportService,
    private val planService: PlanService
) {
    private val logger = KotlinLogging.logger {}

    fun getSummary(planId: UUID): String {
        logger.debug { "Getting summary for plan '$planId'..." }
        val plan = planService.get(planId)
        return summaryReporter.generateReports(plan.meetings)
    }

    fun createGmailDrafts(planId: UUID): Set<String> {
        logger.debug { "Creating Gmail drafts for plan '$planId'..." }
        val plan = planService.get(planId)
        val drafts = gmailDraftReportService.generateReports(plan.meetings)
        return drafts.map { it.id }.toSet()
    }
}
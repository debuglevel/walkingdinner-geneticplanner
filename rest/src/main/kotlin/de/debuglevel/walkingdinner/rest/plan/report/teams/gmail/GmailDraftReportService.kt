package de.debuglevel.walkingdinner.rest.plan.report.teams.gmail


import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.report.Reporter
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReport
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReportService
import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class GmailDraftReportService(
    private val textReportService: TextReportService,
    private val gmailService: GmailService
) : Reporter {
    private val logger = KotlinLogging.logger {}

    override fun generateReports(meetings: Set<Meeting>): Set<TextReport> {
        logger.trace { "Generating Gmail drafts..." }

        val reports = textReportService.generateReports(meetings)
        reports.forEach {
            createDraft(it.team, it.text)
        }

        logger.trace { "Generated Gmail drafts" }
        return reports
    }

    private fun createDraft(team: Team, text: String) {
        val mailaddresses = setOf(team.cook1.mail.mail, team.cook2.mail.mail)
        val subject = "Walking Dinner"

        gmailService.saveDraft(mailaddresses, subject, text)
    }
}
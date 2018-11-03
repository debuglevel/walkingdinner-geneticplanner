package de.debuglevel.walkingdinner.report.teams.gmail


import de.debuglevel.walkingdinner.model.Meeting
import de.debuglevel.walkingdinner.model.team.Team
import de.debuglevel.walkingdinner.report.teams.TextReport
import de.debuglevel.walkingdinner.report.teams.TextReporter


class GmailDraftReporter : TextReporter() {
    val gmail = Gmail

    override fun generateReports(meetings: Set<Meeting>): Set<TextReport> {
        val reports = super.generateReports(meetings)
        reports.forEach {
            run {
                createDraft(it.team, it.text)
            }
        }
        return reports
    }

    private fun createDraft(team: Team, text: String) {
        val mailaddresses = setOf(team.cook1.mail.mail, team.cook2.mail.mail)
        val subject = "Walking Dinner"

        Gmail.saveDraft(mailaddresses, subject, text)
    }
}
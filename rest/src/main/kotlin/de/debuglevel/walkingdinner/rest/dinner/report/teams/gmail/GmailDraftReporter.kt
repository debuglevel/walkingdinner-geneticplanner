package de.debuglevel.walkingdinner.rest.dinner.report.teams.gmail


import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.dinner.report.teams.TextReport
import de.debuglevel.walkingdinner.rest.dinner.report.teams.TextReporter
import de.debuglevel.walkingdinner.rest.participant.Team


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
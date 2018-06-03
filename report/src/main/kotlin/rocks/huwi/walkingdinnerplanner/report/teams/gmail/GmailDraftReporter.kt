package rocks.huwi.walkingdinnerplanner.report.teams.gmail


import rocks.huwi.walkingdinnerplanner.model.Meeting
import rocks.huwi.walkingdinnerplanner.model.team.Team
import rocks.huwi.walkingdinnerplanner.report.teams.TextReport
import rocks.huwi.walkingdinnerplanner.report.teams.TextReporter


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
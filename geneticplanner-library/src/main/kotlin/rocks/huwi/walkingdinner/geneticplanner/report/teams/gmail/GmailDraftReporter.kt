package rocks.huwi.walkingdinner.geneticplanner.report.teams.gmail


import rocks.huwi.walkingdinner.geneticplanner.Meeting
import rocks.huwi.walkingdinner.geneticplanner.report.teams.TextReport
import rocks.huwi.walkingdinner.geneticplanner.report.teams.TextReporter
import rocks.huwi.walkingdinner.geneticplanner.team.Team


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
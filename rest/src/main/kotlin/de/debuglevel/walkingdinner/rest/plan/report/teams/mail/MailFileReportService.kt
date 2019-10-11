package de.debuglevel.walkingdinner.rest.plan.report.teams.mail


import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.report.Reporter
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReportService
import io.micronaut.context.annotation.Property
import mu.KotlinLogging
import javax.inject.Singleton
import javax.mail.internet.MimeMessage

@Singleton
class MailFileReportService(
    @Property(name = "app.walkingdinner.reporters.mail.from-mail-address") private val fromMailAddress: String,
    private val textReportService: TextReportService,
    private val mailService: MailService
) : Reporter {
    private val logger = KotlinLogging.logger {}

    override fun generateReports(meetings: Set<Meeting>): Set<MimeMessage> {
        logger.trace { "Generating mail files..." }

        val reports = textReportService.generateReports(meetings)
        val mimeMessages = reports
            .map {
                buildMailFile(it.team, it.text)
            }
            .toSet()

        logger.trace { "Generated mail files" }
        return mimeMessages
    }

    private fun buildMailFile(team: Team, text: String): MimeMessage {
        logger.trace { "Generating mail file for team '$team'..." }

        val mailaddresses = setOf(team.cook1.mail.mail, team.cook2.mail.mail)
        val subject = "Walking Dinner"

        val mimeMessage = mailService.buildMimeMessage(
            to = mailaddresses,
            from = fromMailAddress,
            subject = subject,
            bodyText = text
        )

        logger.trace { "Generated mail file for team '$team'." }
        return mimeMessage
    }
}
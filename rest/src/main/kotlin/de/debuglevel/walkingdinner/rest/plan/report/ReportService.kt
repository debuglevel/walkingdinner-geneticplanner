package de.debuglevel.walkingdinner.rest.plan.report

import de.debuglevel.walkingdinner.rest.common.ZipService
import de.debuglevel.walkingdinner.rest.plan.PlanService
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.gmail.GmailDraftReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.mail.MailFileReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.mail.MailService
import de.debuglevel.walkingdinner.rest.plan.report.teams.summary.SummaryReporter
import io.micronaut.scheduling.annotation.Async
import mu.KotlinLogging
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Singleton


@Singleton
open class ReportService(
    private val textReportService: TextReportService,
    private val summaryReporter: SummaryReporter,
    private val gmailDraftReportService: GmailDraftReportService,
    private val mailService: MailService,
    private val mailFileReportService: MailFileReportService,
    private val planService: PlanService,
    private val zipService: ZipService
) {
    private val logger = KotlinLogging.logger {}

    fun getSummary(planId: UUID): String {
        logger.debug { "Getting summary for plan '$planId'..." }
        val plan = planService.get(planId)
        val summary = summaryReporter.generateReports(plan.meetings)
        logger.debug { "Got summary for plan '$planId'" }
        return summary
    }

    @Async
    open fun createGmailDrafts(planId: UUID) {
        logger.debug { "Creating Gmail drafts for plan '$planId'..." }
        val plan = planService.get(planId)
        val drafts = gmailDraftReportService.generateReports(plan.meetings)
        logger.debug { "Created Gmail drafts for plan '$planId'" }
    }

    fun getAllMails(planId: UUID): ByteArray {
        logger.debug { "Creating mail files for plan '$planId'..." }
        val plan = planService.get(planId)

        // get all MimeMessages (which are EML when written to file)
        val mimeMessages = mailFileReportService.generateReports(plan.meetings)

        // zip them into an archive
        val zipItems = mimeMessages.map {
            val filename = UUID.randomUUID().toString() + ".eml"

            val outputStream = ByteArrayOutputStream()
            it.writeTo(outputStream)
            val inputStream = outputStream.toByteArray().inputStream()

            ZipService.ZipItem(filename, inputStream)
        }.toSet()

        val byteArrayOutputStream = ByteArrayOutputStream()
        zipService.zip(zipItems, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()

        // return archive
        logger.debug { "Created mail files for plan '$planId'" }
        return bytes
    }
}
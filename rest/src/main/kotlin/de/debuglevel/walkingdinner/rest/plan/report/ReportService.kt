package de.debuglevel.walkingdinner.rest.plan.report

import de.debuglevel.walkingdinner.rest.plan.PlanService
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.gmail.GmailDraftReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.mail.MailFileReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.mail.MailService
import de.debuglevel.walkingdinner.rest.plan.report.teams.summary.SummaryReporter
import mu.KotlinLogging
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.inject.Singleton


@Singleton
class ReportService(
    private val textReportService: TextReportService,
    private val summaryReporter: SummaryReporter,
    private val gmailDraftReportService: GmailDraftReportService,
    private val mailService: MailService,
    private val mailFileReportService: MailFileReportService,
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

    // TODO: creates a corrupt zip file for now
    fun getAllMails(planId: UUID): ByteArray {
        logger.debug { "Creating mail files for plan '$planId'..." }
        val plan = planService.get(planId)

        // get all MimeMessages (which are EML when written to file)
        val mimeMessages = mailFileReportService.generateReports(plan.meetings)

        // zip them into an archive
        val zipItems = mimeMessages.map {
            ZipItem(UUID.randomUUID().toString(), it.inputStream)
        }.toSet()

        val byteArrayOutputStream = ByteArrayOutputStream()
        zip(zipItems, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()

        // return archive
        logger.debug { "Created mail files for plan '$planId'" }
        return bytes
    }

    private data class ZipItem(val filename: String, val inputStream: InputStream)

    private fun zip(zipItems: Set<ZipItem>, zipOutputStream: OutputStream) {
        logger.debug { "Creating zip file..." }

        ZipOutputStream(BufferedOutputStream(zipOutputStream)).use { outputStream ->
            for (zipItem in zipItems) {
                zipItem.inputStream.use { inputStream ->
                    BufferedInputStream(inputStream).use { bufferedInputStream ->
                        val zipEntry = ZipEntry(zipItem.filename)
                        outputStream.putNextEntry(zipEntry)
                        bufferedInputStream.copyTo(outputStream, 1024)
                    }
                }
            }
        }

        logger.debug { "Created zip file" }
    }
}
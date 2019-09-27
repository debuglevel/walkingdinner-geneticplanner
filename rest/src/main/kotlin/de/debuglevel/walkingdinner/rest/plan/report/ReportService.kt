package de.debuglevel.walkingdinner.rest.plan.report

import de.debuglevel.walkingdinner.rest.common.ZipService
import de.debuglevel.walkingdinner.rest.plan.PlanService
import de.debuglevel.walkingdinner.rest.plan.report.teams.TextReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.gmail.CreateGmailDraftsEvent
import de.debuglevel.walkingdinner.rest.plan.report.teams.gmail.GmailDraftReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.gmail.GmailDraftsReport
import de.debuglevel.walkingdinner.rest.plan.report.teams.mail.MailFileReportService
import de.debuglevel.walkingdinner.rest.plan.report.teams.mail.MailService
import de.debuglevel.walkingdinner.rest.plan.report.teams.summary.SummaryReporter
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.runtime.event.annotation.EventListener
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
    private val zipService: ZipService,
    private val eventPublisher: ApplicationEventPublisher
) {
    private val logger = KotlinLogging.logger {}

    private val reports = mapOf<UUID, Report>()

    fun getSummary(planId: UUID): String {
        logger.debug { "Getting summary for plan '$planId'..." }
        val plan = planService.get(planId)
        val summary = summaryReporter.generateReports(plan.meetings)
        logger.debug { "Got summary for plan '$planId'" }
        return summary
    }

    fun createGmailDrafts(planId: UUID) {
        logger.debug { "Publishing event to create Gmail drafts for plan '$planId'..." }

        val plan = planService.get(planId)
        val report = GmailDraftsReport(UUID.randomUUID(), plan)

        val createGmailDraftsEvent = CreateGmailDraftsEvent(report)
        eventPublisher.publishEvent(createGmailDraftsEvent)

        logger.debug { "Published event to create Gmail drafts for plan '$planId'" }
    }

    @EventListener
    @Async
    open fun onCreateGmailDraftsEvent(createGmailDraftsEvent: CreateGmailDraftsEvent) {
        logger.debug { "Creating Gmail drafts for plan '${createGmailDraftsEvent.gmailDraftsReport.plan.id}'..." }

        val drafts = gmailDraftReportService.generateReports(createGmailDraftsEvent.gmailDraftsReport.plan.meetings)
        createGmailDraftsEvent.gmailDraftsReport.drafts.addAll(drafts)

        logger.debug { "Created Gmail drafts for plan '${createGmailDraftsEvent.gmailDraftsReport.plan.id}'" }
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
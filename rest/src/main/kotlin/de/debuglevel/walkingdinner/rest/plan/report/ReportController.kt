package de.debuglevel.walkingdinner.rest.plan.report

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import mu.KotlinLogging
import java.util.*

@Controller("/plans/reports")
class ReportController(private val reportService: ReportService) {
    private val logger = KotlinLogging.logger {}

    @Get("/summary/{planId}")
    @Produces(MediaType.TEXT_PLAIN)
    fun getOnePlaintext(planId: UUID): String {
        logger.debug("Called getOnePlaintext($planId)")
        val summary = reportService.getSummary(planId)
        return summary
    }

    @Post("/gmail/{planId}")
    fun postOnePlaintext(planId: UUID): Set<String> {
        logger.debug("Called postOnePlaintext($planId)")
        val draftIds = reportService.createGmailDrafts(planId)
        return draftIds
    }

    @Get("/mails/{planId}")
    @Produces("application/zip")
    fun getAllMail(planId: UUID): ByteArray {
        logger.debug("Called getAllMail($planId)")
        val bytes = reportService.getAllMails(planId)
        return bytes
    }

//    @Get("/")
//    fun getList(): Set<CalculationResponse> {
//        logger.debug("Called getList()")
//        val calculations = reportService.getAll()
//        return calculations.map { CalculationResponse(it) }.toSet()
//    }

//    @Post("/")
//    fun postOne(calculationRequest: CalculationRequest): CalculationResponse {
//        logger.debug("Called postOne($calculationRequest)")
//
//        // TODO: as this is just a CSV, we could just transfer it as a String
//        val surveyCsv = Base64String(calculationRequest.surveyfile).asString
//        val calculation = reportService.startCalculation(
//            surveyCsv,
//            calculationRequest.populationsSize,
//            calculationRequest.fitnessThreshold,
//            calculationRequest.steadyFitness,
//            calculationRequest.location
//        )
//
//        return CalculationResponse(calculation)
//    }
}
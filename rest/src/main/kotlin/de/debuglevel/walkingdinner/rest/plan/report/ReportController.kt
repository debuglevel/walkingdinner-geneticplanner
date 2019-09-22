package de.debuglevel.walkingdinner.rest.plan.report

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import mu.KotlinLogging
import java.util.*

@Controller("/plans/reports")
class ReportController(private val reportService: ReportService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{planId}")
    @Produces(MediaType.TEXT_PLAIN)
    fun getOnePlaintext(planId: UUID): String {
        logger.debug("Called getOne($planId)")
        val summary = reportService.getSummary(planId)
        return summary
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
package de.debuglevel.walkingdinner.planners.calculation

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging
import java.util.*

@Controller("/calculations")
class CalculationController(private val calculationService: CalculationService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{calculationId}")
    fun getOne(calculationId: UUID): CalculationResponse {
        logger.debug("Called getOne($calculationId)")
        val calculation = calculationService.get(calculationId)
        return calculation.toCalculationResponse()
    }

    @Get("/")
    fun getList(): Set<CalculationResponse> {
        logger.debug("Called getList()")
        val calculations = calculationService.getAll()
        return calculations.map { it.toCalculationResponse() }.toSet()
    }

    @Post("/")
    fun postOne(calculationRequest: CalculationRequest): CalculationResponse {
        logger.debug("Called postOne($calculationRequest)")
        val calculation = calculationRequest.toCalculation()
        calculationService.start(calculation)
        return calculation.toCalculationResponse()
    }
}
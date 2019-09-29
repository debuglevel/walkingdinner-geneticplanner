package de.debuglevel.walkingdinner.rest.plan.calculation

//import spark.kotlin.RouteHandler
import de.debuglevel.walkingdinner.rest.common.Base64String
import de.debuglevel.walkingdinner.rest.common.toUUID
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging

@Controller("/plans/calculations")
class CalculationController(private val calculationService: CalculationService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{calculationId}")
    fun getOne(calculationId: String): CalculationResponse {
        logger.debug("Called getOne($calculationId)")
        val calculation = calculationService.get(calculationId.toUUID())
        return CalculationResponse(calculation)
    }

    @Get("/")
    fun getList(): Set<CalculationResponse> {
        logger.debug("Called getList()")
        val calculations = calculationService.getAll()
        return calculations.map { CalculationResponse(it) }.toSet()
    }

    @Post("/")
    fun postOne(calculationRequest: CalculationRequest): CalculationResponse {
        logger.debug("Called postOne($calculationRequest)")

        // TODO: as this is just a CSV, we could just transfer it as a String
        val surveyCsv = Base64String(calculationRequest.surveyfile).asString
        val calculation = calculationService.startCalculation(
            surveyCsv,
            calculationRequest.populationsSize,
            calculationRequest.fitnessThreshold,
            calculationRequest.steadyFitness
        )

        return CalculationResponse(calculation)
    }
}
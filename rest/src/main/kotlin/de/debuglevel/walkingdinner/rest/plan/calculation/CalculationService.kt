package de.debuglevel.walkingdinner.rest.plan.calculation

import de.debuglevel.walkingdinner.rest.participant.importer.DatabaseBuilder
import de.debuglevel.walkingdinner.rest.plan.Plan
import de.debuglevel.walkingdinner.rest.plan.PlanService
import de.debuglevel.walkingdinner.rest.plan.calculation.client.CalculationClient
import de.debuglevel.walkingdinner.rest.plan.calculation.client.CalculationRequest
import de.debuglevel.walkingdinner.rest.plan.calculation.client.TeamRequest
import de.debuglevel.walkingdinner.rest.plan.client.PlanClient
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class CalculationService(
    private val calculationClient: CalculationClient,
    private val planClient: PlanClient,
    private val planService: PlanService,
    private val databaseBuilder: DatabaseBuilder
) {
    private val logger = KotlinLogging.logger {}

    private val calculations = mutableMapOf<UUID, Calculation>()

    fun get(id: UUID): Calculation {
        logger.debug { "Getting calculation '$id'..." }
        val calculation = calculations.getOrElse(id) { throw CalculationNotFoundException(id) }

        // if calculation has no plan yet, try to fetch it from the microservice
        if (calculation.plan == null) {
            logger.debug { "Calculation ${calculation.id} has no plan yet; fetching it from microservice..." }
            val calculationResponse = calculationClient.getOne(calculation.calculationId!!)
            logger.debug { "Received CalculationResponse: $calculationResponse..." }

            calculation.finished = calculationResponse.finished
            calculation.begin = calculationResponse.begin
            calculation.end = calculationResponse.end

            if (calculation.finished) {
                logger.debug { "Calculation is finished on calculation microservice; fetching plan ${calculationResponse.planId!!} from microservice..." }
                val planResponse = planClient.getOne(calculationResponse.planId!!)

                val plan = Plan(
                    id = planResponse.id, // TODO: will not work as soon as plan is saved; must be null then.
                    meetings = planResponse.meetings.map { it.toMeeting(calculation.teams) }.toSet(),
                    additionalInformation = "TODO"
                )

                planService.add(plan)

                calculation.plan = plan
            }
        }

        logger.debug { "Got calculation: $calculation" }
        return calculation
    }

    fun getAll(): Set<Calculation> {
        logger.debug { "Getting all calculations..." }
        val calculations = calculations
            .map { get(it.key) }
            .toSet()
        logger.debug { "Got calculation: $calculations" }
        return calculations
    }

    fun startCalculation(
        surveyfile: String,
        populationsSize: Int,
        fitnessThreshold: Double,
        steadyFitness: Int
    ): Calculation {
        val database = databaseBuilder.build(surveyfile)
        val teams = database.teams

        val calculation = Calculation(
            UUID.randomUUID(),
            false,
            surveyfile,
            populationsSize,
            fitnessThreshold,
            steadyFitness,
            null,
            teams
        )

        calculations[calculation.id] = calculation

        val calculationRequest = CalculationRequest(
            populationsSize = calculation.populationsSize,
            steadyFitness = calculation.steadyFitness,
            fitnessThreshold = calculation.fitnessThreshold,
            teams = database.teams.map { TeamRequest(it) }
        )

        logger.debug { "Sending CalculationRequest: $calculationRequest..." }
        val calculationResponse = calculationClient.postOne(calculationRequest)
        logger.debug { "Received CalculationResponse: $calculationResponse..." }

        calculation.calculationId = calculationResponse.id

        return calculation
    }

    class CalculationNotFoundException(planId: UUID) : Exception("Plan $planId not found")
}
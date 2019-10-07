package de.debuglevel.walkingdinner.planners.plan

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import mu.KotlinLogging
import java.util.*

@Controller("/plans")
class PlanController(private val planService: PlanService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{planId}")
    fun getOne(planId: UUID): PlanResponse {
        logger.debug("Called getOne($planId)")
        val plan = planService.get(planId)
        return plan.toPlanResponse()
    }

    @Get("/")
    fun getList(): Set<PlanResponse> {
        logger.debug("Called getList()")
        val plans = planService.getAll()
        return plans.map { it.toPlanResponse() }.toSet()
    }
}
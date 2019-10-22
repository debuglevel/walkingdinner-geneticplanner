package de.debuglevel.walkingdinner.rest.plan

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import mu.KotlinLogging
import java.util.*

@Controller("/plans")
class PlanController(private val planService: PlanService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{planId}")
    fun getOne(planId: UUID): Plan {
        logger.debug("Called getOne($planId)")
        val plan = planService.get(planId)
        // TODO: maybe replace Plan by a PlanResponse
        return plan
    }

    @Get("/")
    fun getList(): Set<Plan> {
        logger.debug("Called getList()")
        val plans = planService.getAll()
        // TODO: maybe replace Plan by a PlanResponse
        return plans
    }
}
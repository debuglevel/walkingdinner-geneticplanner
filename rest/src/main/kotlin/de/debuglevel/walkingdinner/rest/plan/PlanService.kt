package de.debuglevel.walkingdinner.rest.plan

import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class PlanService(
    private val planRepository: PlanRepository
) {
    private val logger = KotlinLogging.logger {}

    fun get(id: UUID): Plan {
        logger.debug { "Getting plan '$id'..." }
        val plan = planRepository.findById(id).orElseThrow { PlanNotFoundException(id) }
        logger.debug { "Got plan: $plan" }
        return plan
    }

    fun getAll(): Set<Plan> {
        logger.debug { "Getting all plans..." }
        val plans = planRepository.findAll().toSet()
        logger.debug { "Got all plans" }
        return plans
    }

    fun add(plan: Plan): Plan {
        logger.debug { "Saving plan '$plan'..." }
        val savedPlan = planRepository.save(plan)
        logger.debug { "Saved plan: $savedPlan" }
        return savedPlan
    }

    class PlanNotFoundException(planId: UUID) : Exception("Plan $planId not found")
}
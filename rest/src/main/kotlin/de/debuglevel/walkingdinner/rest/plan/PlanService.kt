package de.debuglevel.walkingdinner.rest.plan

import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class PlanService(
    private val planRepository: PlanRepository
) {
    private val logger = KotlinLogging.logger {}

    private val plans = mutableMapOf<UUID, Plan>()

    fun get(id: UUID): Plan {
        logger.debug { "Getting plan '$id'..." }
        val plan = plans.getOrElse(id) { throw PlanNotFoundException(id) }
        logger.debug { "Got plan: $plan" }
        return plan
    }

    fun getAll(): Set<Plan> {
        logger.debug { "Getting all plans..." }
        val plans = plans
            .map { get(it.key) }
            .toSet()
        logger.debug { "Got all plans" }
        return plans
    }

    fun add(plan: Plan) {
        logger.debug { "Adding plan $plan..." }
        plans[plan.id!!] = plan
        logger.debug { "Added plan $plan" }
    }

    // TODO: replace above add() in-memory database with saving to real database
    fun addX(plan: Plan): Plan {
        logger.debug { "Saving plan '$plan'..." }

        val savedPlan = planRepository.save(plan)

        logger.debug { "Saved person: $savedPlan" }
        return plan
    }

    class PlanNotFoundException(planId: UUID) : Exception("Plan $planId not found")
}
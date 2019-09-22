package de.debuglevel.walkingdinner.rest.plan

import io.micronaut.context.annotation.Property
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class PlanService(
    @Property(name = "app.walkingdinner.planners.threads") val threadCount: Int
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
        plans[plan.id] = plan
        logger.debug { "Added plan $plan" }
    }

    class PlanNotFoundException(planId: UUID) : Exception("Plan $planId not found")
}
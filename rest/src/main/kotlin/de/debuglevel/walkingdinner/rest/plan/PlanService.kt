package de.debuglevel.walkingdinner.rest.plan

import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class PlanService {
    private val logger = KotlinLogging.logger {}

    fun get(planId: String): PlanDTO {
        return PlanDTO(true, null)
    }

    fun getAll(): Set<PlanDTO> {
        return (1..5)
            .map {
                PlanDTO(
                    true,
                    null
                )
            }
            .toSet()
    }
}
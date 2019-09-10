package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.rest.plan.Plan
import java.util.*

data class OrganisationDTO(
    val id: UUID,
    val name: String
) {

    val plans: Set<Plan> = setOf()
}
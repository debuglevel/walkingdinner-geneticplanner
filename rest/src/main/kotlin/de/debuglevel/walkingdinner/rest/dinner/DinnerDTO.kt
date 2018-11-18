package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.model.Plan

data class DinnerDTO(val id: Int,
                     val name: String) {

    val plans: Set<Plan> = setOf()
}
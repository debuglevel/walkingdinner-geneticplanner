package de.debuglevel.walkingdinner.rest.plan.calculation

import java.util.*

data class CalculationResponse(
    /**
     * UUID of the calculation
     */
    val id: UUID,
    /**
     * Whether the calculation of the plan has finished or is still in progress
     */
    val finished: Boolean,
    /**
     * UUID of the plan, once it is calculated
     */
    val planId: UUID?
) {
    constructor(calculation: Calculation) :
            this(
                calculation.id,
                calculation.finished,
                calculation.plan?.id
            )
}

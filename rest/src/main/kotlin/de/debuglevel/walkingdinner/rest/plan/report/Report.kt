package de.debuglevel.walkingdinner.rest.plan.report

import de.debuglevel.walkingdinner.rest.plan.Plan
import java.util.*

interface Report {
    val id: UUID?
    val plan: Plan
}
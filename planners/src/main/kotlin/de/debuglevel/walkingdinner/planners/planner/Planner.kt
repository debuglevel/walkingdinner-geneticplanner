package de.debuglevel.walkingdinner.planners.planner

import de.debuglevel.walkingdinner.planners.plan.Plan

interface Planner {
    fun plan(): Plan
}
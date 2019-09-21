package de.debuglevel.walkingdinner.rest.dinner.planner

import de.debuglevel.walkingdinner.rest.plan.Plan

interface Planner {
    fun plan(): Plan
}
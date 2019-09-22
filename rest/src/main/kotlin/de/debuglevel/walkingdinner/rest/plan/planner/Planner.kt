package de.debuglevel.walkingdinner.rest.plan.planner

import de.debuglevel.walkingdinner.rest.plan.Plan

interface Planner {
    fun plan(): Plan
}
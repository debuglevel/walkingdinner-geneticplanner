package de.debuglevel.walkingdinner.planner

import de.debuglevel.walkingdinner.rest.plan.Plan

interface Planner {
    fun plan(): Plan
}
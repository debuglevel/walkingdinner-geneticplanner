package de.debuglevel.walkingdinner.planner

import de.debuglevel.walkingdinner.model.Plan

interface Planner {
    fun plan(): Plan
}
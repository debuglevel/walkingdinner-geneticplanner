package de.debuglevel.walkingdinner.rest.plan.report

import de.debuglevel.walkingdinner.rest.Meeting

interface Reporter {
    fun generateReports(meetings: Set<Meeting>): Any
}
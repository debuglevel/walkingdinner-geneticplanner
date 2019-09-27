package de.debuglevel.walkingdinner.rest.plan.report.teams.gmail

import com.google.api.services.gmail.model.Draft
import de.debuglevel.walkingdinner.rest.plan.Plan
import de.debuglevel.walkingdinner.rest.plan.report.Report
import java.util.*

data class GmailDraftsReport(
    override val id: UUID?,
    override val plan: Plan,
    val drafts: MutableSet<Draft> = mutableSetOf()
) : Report
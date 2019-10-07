package de.debuglevel.walkingdinner.rest.plan.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import java.util.*

@Client("plan", path = "/plans")
interface PlanClient {
    @Get("/{planId}")
    fun getOne(planId: UUID): PlanResponse

    @Get("/")
    fun getList(): Set<PlanResponse>
}
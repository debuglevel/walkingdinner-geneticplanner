package de.debuglevel.walkingdinner.rest.plan.calculation.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import java.util.*

@Client("calculation", path = "/calculations")
//@Client("http://localhost:8081/calculations")
interface CalculationClient {
    @Get("/{calculationId}")
    fun getOne(calculationId: UUID): CalculationResponse

    @Get("/")
    fun getList(): Set<CalculationResponse>

    @Post("/")
    fun postOne(calculationRequest: CalculationRequest): CalculationResponse
}
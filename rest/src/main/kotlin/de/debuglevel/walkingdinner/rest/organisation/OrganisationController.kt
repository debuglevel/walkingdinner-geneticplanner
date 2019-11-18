package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.rest.common.ElementNotFoundException
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging
import java.util.*

@Controller("/organisations")
class OrganisationController(private val organisationService: OrganisationService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{organisationId}")
    fun getOne(organisationId: UUID): HttpResponse<OrganisationResponse> {
        logger.debug("Called getOne($organisationId)")

        return try {
            val organisation = organisationService.get(organisationId)
            HttpResponse.ok(OrganisationResponse(organisation))
        } catch (e: ElementNotFoundException) {
            HttpResponse.notFound<OrganisationResponse>()
        }
    }

    @Get("/")
    fun getList(): Set<OrganisationResponse> {
        logger.debug("Called getList()")
        return organisationService.getAll().map {
            OrganisationResponse(it)
        }.toSet()
    }

    @Post("/")
    fun postOne(organisationRequest: OrganisationRequest): HttpResponse<OrganisationResponse> {
        logger.debug("Called postOne()")

        return try {
            val organisation = Organisation(name = organisationRequest.name)
            val savedOrganisation = organisationService.save(organisation)
            HttpResponse.created(OrganisationResponse(savedOrganisation))
        } catch (e: ElementNotFoundException) {
            HttpResponse.badRequest<OrganisationResponse>()
        }
    }
}
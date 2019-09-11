package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.repository.ElementNotFoundException
import de.debuglevel.walkingdinner.utils.toUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging

@Controller("/organisations")
class OrganisationController(private val organisationService: OrganisationService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{organisationId}")
    fun getOne(organisationId: String): HttpResponse<OrganisationResponse> {
        logger.debug("Called getOne($organisationId)")

        return try {
            val organisation = organisationService.get(organisationId.toUUID())
            HttpResponse.ok(OrganisationResponse(organisation.id, organisation.name))
        } catch (e: ElementNotFoundException) {
            HttpResponse.notFound<OrganisationResponse>()
        }
    }

    @Get("/")
    fun getList(): Set<OrganisationResponse> {
        logger.debug("Called getList()")
        return organisationService.getAll().map {
            OrganisationResponse(it.id, it.name)
        }.toSet()
    }

    @Post("/")
    fun postOne(organisationRequest: OrganisationRequest): HttpResponse<OrganisationResponse> {
        logger.debug("Called postOne()")

        return try {
            val organisation = Organisation(organisationRequest.name)
            val savedOrganisation = organisationService.save(organisation)
            HttpResponse.created(OrganisationResponse(savedOrganisation.id, savedOrganisation.name))
        } catch (e: ElementNotFoundException) {
            HttpResponse.badRequest<OrganisationResponse>()
        }
    }

//    fun getOneHtml(): RouteHandler.() -> String {
//        return {
//            val model = HashMap<String, Any>()
//            MustacheTemplateEngine().render(ModelAndView(model, "organisation/show.html.mustache"))
//        }
//    }

//    fun getListHtml(): RouteHandler.() -> String {
//        return {
//            val model = HashMap<String, Any>()
//            MustacheTemplateEngine().render(ModelAndView(model, "organisation/list.html.mustache"))
//        }
//    }

    /*
    fun getAddFormHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/participants'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "participant/add.html.mustache"))
        }
    }*/
}
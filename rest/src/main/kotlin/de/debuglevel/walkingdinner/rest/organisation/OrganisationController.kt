package de.debuglevel.walkingdinner.rest.organisation

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import mu.KotlinLogging

@Controller("/organisations")
class OrganisationController(private val organisationService: OrganisationService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{organisationId}")
    fun getOne(organisationId: String): OrganisationDTO {
        logger.debug("Called getOne($organisationId)")
        return organisationService.get(organisationId)
    }

    @Get("/")
    fun getList(): Set<OrganisationDTO> {
        logger.debug("Called getList()")
        return organisationService.getAll()
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
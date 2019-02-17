package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.repository.MongoDatabase
import de.debuglevel.walkingdinner.repository.OrganisationRepository
import de.debuglevel.walkingdinner.rest.responsetransformer.JsonTransformer
import mu.KotlinLogging
import spark.ModelAndView
import spark.kotlin.RouteHandler
import spark.template.mustache.MustacheTemplateEngine
import java.util.*

object OrganisationController {
    private val logger = KotlinLogging.logger {}

    /*
    fun postOne(): RouteHandler.() -> Any {
        return {
            logger.debug("Got POST request on '/dinners' with content-type '${request.contentType()}'")

            if (!request.contentType().startsWith("multipart/form-data")) {
                logger.debug { "Declining POST request with unsupported content-type '${request.contentType()}'" }
                throw Exception("Content-Type ${request.contentType()} not supported.")
            }
        }
    }*/

    fun getOneHtml(): RouteHandler.() -> String {
        return {
            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "organisation/show.html.mustache"))
        }
    }

    fun getOneJson(): RouteHandler.() -> String {
        return {
            type(contentType = "application/json")
            val organisationId = request.params(":organisationId")

            try {
                val organisation = OrganisationRepository.get(organisationId)
                JsonTransformer.render(organisation)
            } catch (e: MongoDatabase.ObjectNotFoundException) {
                status(404)
                "{'message':'organisation not found'}"
            }
        }
    }

    fun getListHtml(): RouteHandler.() -> String {
        return {
            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "organisation/list.html.mustache"))
        }
    }

    fun getListJson(): RouteHandler.() -> String {
        return {
            type(contentType = "application/json")
            val organisations = OrganisationRepository.getAll()

            JsonTransformer.render(organisations)
        }
    }

    /*
    fun getAddFormHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/participants'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "participant/add.html.mustache"))
        }
    }*/
}
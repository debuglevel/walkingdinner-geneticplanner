package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.rest.responsetransformer.JsonTransformer
import de.debuglevel.walkingdinner.rest.toUUID
import mu.KotlinLogging
import spark.ModelAndView
import spark.kotlin.RouteHandler
import spark.template.mustache.MustacheTemplateEngine
import java.util.*

object DinnerController {
    private val logger = KotlinLogging.logger {}

    private val mockDinners = setOf<DinnerDTO>(
            DinnerDTO(UUID.fromString("436d24f1-03ae-4ea4-912b-45d0972577fd"), "Closed Beta Walking Dinner"),
            DinnerDTO(UUID.fromString("66deabef-2290-47fb-9146-3560afd0a47c"), "Public Beta Walking Dinner"),
            DinnerDTO(UUID.fromString("cc05584a-3652-4e12-8e6f-28ed0605277a"), "First official Walking Dinner"),
            DinnerDTO(UUID.fromString("1cca2290-d870-47e4-8463-8f5733193a09"), "Second official Walking Dinner")
    )

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
            MustacheTemplateEngine().render(ModelAndView(model, "dinner/show.html.mustache"))
        }
    }

    fun getOneJson(): RouteHandler.() -> String {
        return {
            type(contentType = "application/json")
            val dinnerId = request.params(":dinnerId").toUUID()
            val dinner = mockDinners.firstOrNull { it.id == dinnerId }

            if (dinner == null) {
                status(404)
                "{'message':'dinner not found'}"
            } else {
                JsonTransformer.render(dinner)
            }
        }
    }

    fun getListHtml(): RouteHandler.() -> String {
        return {
            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "dinner/list.html.mustache"))
        }
    }

    fun getListJson(): RouteHandler.() -> String {
        return {
            type(contentType = "application/json")
            val dinners = mockDinners

            JsonTransformer.render(dinners)
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
package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.rest.responsetransformer.JsonTransformer
import mu.KotlinLogging
import spark.ModelAndView
import spark.kotlin.RouteHandler
import spark.template.mustache.MustacheTemplateEngine

object DinnerController {
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

    fun getOne(): RouteHandler.() -> String {
        return {
            val dinnerId = request.params(":dinnerId").toInt()
            logger.debug("Got GET request on '/dinners/$dinnerId'")

            JsonTransformer.render(DinnerDTO(dinnerId, "Dinner $dinnerId"))
        }
    }

    fun getOneHtml(): RouteHandler.() -> String {
        return {
            val dinnerId = request.params(":dinnerId").toInt()
            logger.debug("Got GET request on '/dinners/$dinnerId'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "dinner/show.html.mustache"))
        }
    }

    fun getList(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/dinners/'")

            val dinners = setOf<DinnerDTO>(
                    DinnerDTO(1, "Dinner 1"),
                    DinnerDTO(2, "Dinner 2")
            )

            JsonTransformer.render(dinners)
        }
    }

    fun getListHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/dinners/'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "dinner/list.html.mustache"))
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
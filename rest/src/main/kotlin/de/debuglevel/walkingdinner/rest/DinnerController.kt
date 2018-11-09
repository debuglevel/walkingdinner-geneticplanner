package de.debuglevel.walkingdinner.rest

import mu.KotlinLogging

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

    /*
    fun getOne(): RouteHandler.() -> String {
        return {
            val id = request.params(":id").toInt()
            logger.debug("Got GET request on '/participants/$id'")

        }
    }*/

    /*
    fun getAddFormHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/participants'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "participant/add.html.mustache"))
        }
    }*/
}
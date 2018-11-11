package de.debuglevel.walkingdinner.rest.participant

import com.google.gson.Gson
import mu.KotlinLogging
import spark.ModelAndView
import spark.kotlin.RouteHandler
import spark.template.mustache.MustacheTemplateEngine


object ParticipantController {
    private val logger = KotlinLogging.logger {}

    fun postOne(): RouteHandler.() -> Any {
        return {
            logger.debug("Got POST request on '/participants' with content-type '${request.contentType()}'")

            if (!request.contentType().startsWith("application/json")) {
                logger.debug { "Declining POST request with unsupported content-type '${request.contentType()}'" }
                throw Exception("Content-Type ${request.contentType()} not supported.")
            }

            val participant = Gson().fromJson(request.body(), ParticipantDTO::class.java)


            val x = participant.toTeam()


            ""
        }
    }

    /*
    fun getOne(): RouteHandler.() -> String {
        return {
            val id = request.params(":id").toInt()
            logger.debug("Got GET request on '/participants/$id'")

        }
    }*/

    fun getAddFormHtml(): RouteHandler.() -> String {
        return {
            logger.debug("Got GET request on '/participants'")

            val model = HashMap<String, Any>()
            MustacheTemplateEngine().render(ModelAndView(model, "participant/add.html.mustache"))
        }
    }
}
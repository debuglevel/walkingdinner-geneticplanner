package de.debuglevel.walkingdinner.rest.participant

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import mu.KotlinLogging


@Controller("/participants")
class ParticipantController(private val participantService: ParticipantService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{participantId}")
    fun getOne(participantId: String): ParticipantDTO {
        logger.debug("Called getOne($participantId)")
        return participantService.get(participantId)
    }

    @Get("/")
    fun getList(): Set<ParticipantDTO> {
        logger.debug("Called getList()")
        return participantService.getAll()
    }
//    fun postOne(): RouteHandler.() -> Any {
//        return {
//            logger.debug("Got POST request on '/participants' with content-type '${request.contentType()}'")
//
//            if (!request.contentType().startsWith("application/json")) {
//                logger.debug { "Declining POST request with unsupported content-type '${request.contentType()}'" }
//                throw Exception("Content-Type ${request.contentType()} not supported.")
//            }
//
//            val participant = Gson().fromJson(request.body(), ParticipantDTO::class.java)
//
//
//            val team = participant.toTeam()
//
//            TeamRepository.add(team)
//
//            ""
//        }
//    }

    /*
    fun getOne(): RouteHandler.() -> String {
        return {
            val id = request.params(":id").toInt()
            logger.debug("Got GET request on '/participants/$id'")

        }
    }*/

//    fun getAddFormHtml(): RouteHandler.() -> String {
//        return {
//            logger.debug("Got GET request on '/participants'")
//
//            val model = HashMap<String, Any>()
//            MustacheTemplateEngine().render(ModelAndView(model, "participant/add.html.mustache"))
//        }
//    }
}
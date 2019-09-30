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
}
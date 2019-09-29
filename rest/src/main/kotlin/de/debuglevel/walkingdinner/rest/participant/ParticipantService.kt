package de.debuglevel.walkingdinner.rest.participant

import mu.KotlinLogging
import javax.inject.Singleton

@Singleton
class ParticipantService {
    private val logger = KotlinLogging.logger {}

    fun get(participantId: String): ParticipantDTO {
        return ParticipantDTO(
            null,
            "Mock-Chef $participantId",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null

        )
    }

    fun getAll(): Set<ParticipantDTO> {
        return (1..5)
            .map {
                ParticipantDTO(
                    null,
                    "Mock-Chef $it",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null

                )
            }
            .toSet()
    }
}
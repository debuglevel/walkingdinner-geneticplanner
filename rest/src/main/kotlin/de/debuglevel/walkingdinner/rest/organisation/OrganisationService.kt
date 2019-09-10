package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.utils.toUUID
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class OrganisationService {
    private val logger = KotlinLogging.logger {}

    fun get(organisationId: String): OrganisationDTO {
        return OrganisationDTO(organisationId.toUUID(), "Mock-Organisation $organisationId")
    }

    fun getAll(): Set<OrganisationDTO> {
        return (1..5)
            .map {
                OrganisationDTO(
                    UUID(0, it.toLong()),
                    "Mock-Organisation $it"
                )
            }
            .toSet()
    }
}
package de.debuglevel.walkingdinner.rest.organisation

import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class OrganisationService(
    private val organisationRepository: OrganisationRepository
) {
    private val logger = KotlinLogging.logger {}

    fun get(id: UUID): Organisation {
        logger.debug { "Getting organisation with ID '$id'..." }
        val organisation = organisationRepository.findById(id).orElseThrow { OrganisationNotFoundException(id) }
        logger.debug { "Got organisation: $organisation" }
        return organisation
    }

    fun getAll(): Set<Organisation> {
        logger.debug { "Getting all organisations..." }
        val organisations = organisationRepository.findAll().toSet()
        logger.debug { "Got all organisations" }
        return organisations
    }

    fun save(organisation: Organisation): Organisation {
        logger.debug { "Saving organisation '$organisation'..." }
        val savedOrganisation = organisationRepository.save(organisation)
        logger.debug { "Saved organisation: $savedOrganisation" }
        return savedOrganisation
    }

    class OrganisationNotFoundException(organisationId: UUID) : Exception("Organisation $organisationId not found")
}

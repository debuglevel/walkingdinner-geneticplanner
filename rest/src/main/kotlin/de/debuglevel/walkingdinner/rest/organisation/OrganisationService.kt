package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.repository.ElementNotFoundException
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class OrganisationService(
    private val organisationRepository: OrganisationRepository
) {
    private val logger = KotlinLogging.logger {}

    /**
     * @throws ElementNotFoundException if no element with the given id exists
     */
    fun get(id: UUID): Organisation {
        logger.debug { "Getting organisation with ID '$id'..." }

        val organisation = organisationRepository.findById(id).orElseThrow { ElementNotFoundException(id) }

        logger.debug { "Got organisation with ID '$id': $organisation" }
        return organisation
    }

    fun getAll(): Set<Organisation> {
        logger.debug { "Getting all organisations..." }

        val organisations = organisationRepository.findAll().toSet()

        logger.debug { "Got all organisations" }
        return organisations
    }

    fun save(organisation: Organisation): Organisation {
        logger.debug { "Saving organisation..." }

        val savedOrganisation = organisationRepository.save(organisation)

        logger.debug { "Saved organisation" }
        return savedOrganisation
    }
}

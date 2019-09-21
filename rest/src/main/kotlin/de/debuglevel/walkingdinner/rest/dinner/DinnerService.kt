package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.rest.common.ElementNotFoundException
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class DinnerService(
    private val dinnerRepository: DinnerRepository
) {
    private val logger = KotlinLogging.logger {}

    /**
     * @throws ElementNotFoundException if no element with the given id exists
     */
    fun get(id: UUID): Dinner {
        logger.debug { "Getting dinner with ID '$id'..." }

        val dinner = dinnerRepository.findById(id).orElseThrow {
            ElementNotFoundException(
                id
            )
        }

        logger.debug { "Got dinner with ID '$id': $dinner" }
        return dinner
    }

    fun getAll(): Set<Dinner> {
        logger.debug { "Getting all dinners..." }

        val dinners = dinnerRepository.findAll().toSet()

        logger.debug { "Got all dinners" }
        return dinners
    }

    fun save(dinner: Dinner): Dinner {
        logger.debug { "Saving dinner..." }

        val savedOrganisation = dinnerRepository.save(dinner)

        logger.debug { "Saved dinner" }
        return savedOrganisation
    }
}
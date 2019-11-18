package de.debuglevel.walkingdinner.rest.dinner

import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class DinnerService(
    private val dinnerRepository: DinnerRepository
) {
    private val logger = KotlinLogging.logger {}

    fun get(id: UUID): Dinner {
        logger.debug { "Getting dinner '$id'..." }
        val dinner = dinnerRepository.findById(id).orElseThrow { DinnerNotFoundException(id) }
        logger.debug { "Got dinner: $dinner" }
        return dinner
    }

    @Transactional
    open fun getAll(): Set<Dinner> {
        logger.debug { "Getting all dinners..." }
        val dinners = dinnerRepository.findAll().toSet()
        logger.debug { "Got all dinners" }
        return dinners
    }

    fun save(dinner: Dinner): Dinner {
        logger.debug { "Saving dinner '$dinner'..." }
        val savedDinner = dinnerRepository.save(dinner)
        logger.debug { "Saved dinner: $savedDinner" }
        return savedDinner
    }

    class DinnerNotFoundException(dinnerId: UUID) : Exception("Dinner $dinnerId not found")
}
package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.utils.toUUID
import mu.KotlinLogging
import java.util.*
import javax.inject.Singleton

@Singleton
class DinnerService {
    private val logger = KotlinLogging.logger {}

    fun get(dinnerId: String): DinnerDTO {
        return DinnerDTO(dinnerId.toUUID(), "Mock-Dinner $dinnerId")
    }

    fun getAll(): Set<DinnerDTO> {
        return (1..5)
            .map {
                DinnerDTO(
                    UUID(0, it.toLong()),
                    "Mock-Dinner $it"
                )
            }
            .toSet()
    }
}
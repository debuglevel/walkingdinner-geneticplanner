package de.debuglevel.walkingdinner.rest.dinner

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface DinnerRepository : CrudRepository<Dinner, UUID>
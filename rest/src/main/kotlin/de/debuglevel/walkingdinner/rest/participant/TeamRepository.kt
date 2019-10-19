package de.debuglevel.walkingdinner.rest.participant

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface TeamRepository : CrudRepository<Team, UUID>
package de.debuglevel.walkingdinner.rest.plan

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface PlanRepository : CrudRepository<Plan, UUID>
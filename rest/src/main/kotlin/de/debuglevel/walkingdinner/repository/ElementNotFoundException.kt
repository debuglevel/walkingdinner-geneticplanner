package de.debuglevel.walkingdinner.repository

import java.util.*

data class ElementNotFoundException(
    val id: UUID
) : Exception("No element found with ID $id")
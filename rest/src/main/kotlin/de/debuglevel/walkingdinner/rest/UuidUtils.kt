package de.debuglevel.walkingdinner.rest

import java.util.*

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}
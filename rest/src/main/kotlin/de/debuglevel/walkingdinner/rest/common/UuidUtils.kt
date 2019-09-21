package de.debuglevel.walkingdinner.rest.common

import java.util.*

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}
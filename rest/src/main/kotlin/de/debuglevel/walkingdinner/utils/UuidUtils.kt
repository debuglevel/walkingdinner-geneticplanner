package de.debuglevel.walkingdinner.utils

import java.util.*

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}
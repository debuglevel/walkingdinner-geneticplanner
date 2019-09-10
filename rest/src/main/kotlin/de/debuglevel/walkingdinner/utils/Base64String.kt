package de.debuglevel.walkingdinner.utils

import java.util.*

data class Base64String(val value: String) {
    val asString: String
        get() {
            return String(Base64.getDecoder().decode(value))
        }
}
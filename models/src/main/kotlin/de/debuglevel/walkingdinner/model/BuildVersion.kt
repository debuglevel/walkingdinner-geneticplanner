package de.debuglevel.walkingdinner.model

object BuildVersion {
    val buildVersion: String
        get() {
            return BuildVersion::class.java.`package`.implementationVersion ?: "<unknown version>"
        }

    val buildTitle: String
        get() {
            return BuildVersion::class.java.`package`.implementationTitle ?: "<unknown title>"
        }
}
package de.debuglevel.walkingdinner.model

data class Plan(val additionalInformation: String,
                val meetings: Set<Meeting>)

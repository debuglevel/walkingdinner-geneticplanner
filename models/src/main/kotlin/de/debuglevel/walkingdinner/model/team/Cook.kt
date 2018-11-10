package de.debuglevel.walkingdinner.model.team

data class Cook(val name: Name,
                val mail: MailAddress,
                val phoneNumber: PhoneNumber) {
    override fun toString(): String {
        return "$name ($mail, $phoneNumber)"
    }
}
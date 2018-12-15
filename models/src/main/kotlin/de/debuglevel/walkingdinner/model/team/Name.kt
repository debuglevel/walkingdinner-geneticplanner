package de.debuglevel.walkingdinner.model.team

data class Name(val name: String) {
    val firstname: String by lazy { extractFirstname() }

    val lastname: String by lazy { extractLastname() }

    val abbreviatedName: String by lazy {
        val firstletter = firstname.toCharArray().first()
        "$firstletter. $lastname"
    }

    private fun extractFirstname(): String {
        return this.name.split(" ").first()
    }

    private fun extractLastname(): String {
        return this.name.split(" ").last()
    }

    override fun toString(): String {
        return name
    }
}
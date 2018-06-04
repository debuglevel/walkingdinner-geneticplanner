package rocks.huwi.walkingdinnerplanner.model.team

data class Name(val name: String) {
    val firstname: String = extractFirstname()
    val lastname: String = extractLastname()
    val abbreviatedName: String = buildAbbreviatedName()

    private fun extractFirstname(): String {
        return this.name.split(" ").first()
    }

    private fun extractLastname(): String {
        return this.name.split(" ").last()
    }

    fun buildAbbreviatedName(): String {
        val firstletter = firstname.toCharArray().first()
        return "$firstletter. $lastname"
    }

    override fun toString(): String {
        return name
    }
}
data class Team(var name: String, var diet: String) {
    fun isCompatibleDiet(o: Team) = diet == o.diet

    override fun toString(): String {
        return "$name ($diet)"
    }
}
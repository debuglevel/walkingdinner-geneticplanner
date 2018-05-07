class Database {

    var teams: MutableList<Team> = mutableListOf()

    fun initializeTeams() {
        var index = 0
        teams.add(Team("Team " + (++index), "vegan"))
        teams.add(Team("Team " + (++index), "vegan"))
        teams.add(Team("Team " + (++index), "vegan"))
        teams.add(Team("Team " + (++index), "vegan"))
        teams.add(Team("Team " + (++index), "vegan"))
        teams.add(Team("Team " + (++index), "vegan"))
        teams.add(Team("Team " + (++index), "vegetarisch"))
        teams.add(Team("Team " + (++index), "vegetarisch"))
        teams.add(Team("Team " + (++index), "vegetarisch"))
        teams.add(Team("Team " + (++index), "vegetarisch"))
        teams.add(Team("Team " + (++index), "vegetarisch"))
        teams.add(Team("Team " + (++index), "vegetarisch"))
        teams.add(Team("Team " + (++index), "omnivore"))
        teams.add(Team("Team " + (++index), "omnivore"))
        teams.add(Team("Team " + (++index), "omnivore"))
        teams.add(Team("Team " + (++index), "omnivore"))
        teams.add(Team("Team " + (++index), "omnivore"))
        teams.add(Team("Team " + (++index), "omnivore"))

    }
}
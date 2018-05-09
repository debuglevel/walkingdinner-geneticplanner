import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class Database {

    var teams: MutableList<Team> = mutableListOf()

    fun initializeTeams()
    {
        var fileReader: BufferedReader? = null
        var csvToBean: CsvToBean<Team>?

        try {
            fileReader = BufferedReader(FileReader("Teams_aufbereitet.csv"))
            csvToBean = CsvToBeanBuilder<Team>(fileReader)
                    .withType(Team::class.java)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()

            println("Reading CSV")
            val teams = csvToBean.parse()

            for ((index, team) in teams.withIndex()) {
                team.id = index.toLong()+1
            }

            for (team in teams) {
                println(team)
            }
            this.teams.addAll(teams);
        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } finally {
            try {
                fileReader!!.close()
            } catch (e: IOException) {
                println("Closing fileReader/csvParser Error!")
                e.printStackTrace()
            }
        }
    }

//    fun initializeTeams() {
//        var index = 0
//        teams.add(Team("Team " + (++index), "vegan"))
//        teams.add(Team("Team " + (++index), "vegan"))
//        teams.add(Team("Team " + (++index), "vegan"))
//        teams.add(Team("Team " + (++index), "vegan"))
//        teams.add(Team("Team " + (++index), "vegan"))
//        teams.add(Team("Team " + (++index), "vegan"))
//        teams.add(Team("Team " + (++index), "vegetarisch"))
//        teams.add(Team("Team " + (++index), "vegetarisch"))
//        teams.add(Team("Team " + (++index), "vegetarisch"))
//        teams.add(Team("Team " + (++index), "vegetarisch"))
//        teams.add(Team("Team " + (++index), "vegetarisch"))
//        teams.add(Team("Team " + (++index), "vegetarisch"))
//        teams.add(Team("Team " + (++index), "omnivore"))
//        teams.add(Team("Team " + (++index), "omnivore"))
//        teams.add(Team("Team " + (++index), "omnivore"))
//        teams.add(Team("Team " + (++index), "omnivore"))
//        teams.add(Team("Team " + (++index), "omnivore"))
//        teams.add(Team("Team " + (++index), "omnivore"))
//
//    }
}
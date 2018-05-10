import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class Database {
    lateinit var teams: List<Team>

    fun print()=
        teams.forEach { println(it) }

    fun initializeTeams() {
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
                team.id = index.toLong() + 1
            }

            this.teams = teams;
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
}
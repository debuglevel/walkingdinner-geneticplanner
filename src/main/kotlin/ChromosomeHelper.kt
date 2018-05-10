import io.jenetics.Chromosome
import io.jenetics.EnumGene
import io.jenetics.Genotype
import java.util.HashSet
import java.util.stream.Collectors

class ChromosomeHelper {
    companion object {
        fun toMeetings(chromosome: Chromosome<EnumGene<Team>>, name: String): Set<Meeting> {
            val teams = chromosome.stream()
                    .map { g -> g.allele }
                    .collect(Collectors.toList())

            val meetings = HashSet<Meeting>()
            val meetingsCount = teams.size / 3

            for (meetingIdx in 0 until meetingsCount) {
                val meetingTeams = arrayOf(
                        teams[meetingIdx * 3 + 0],
                        teams[meetingIdx * 3 + 1],
                        teams[meetingIdx * 3 + 2])
                meetings.add(Meeting(meetingTeams, name))
            }

            return meetings
        }

        fun print(gt: Genotype<EnumGene<Team>>) {
            for (idxChromosome in 0..(gt.length()-1)) {
                println()
                println("== Course $idxChromosome")

                val meetings = ChromosomeHelper.toMeetings(
                        gt.getChromosome(idxChromosome),
                        "Gang $idxChromosome")

                for (meeting in meetings) {
                    println(meeting)
                }
            }
        }
    }
}
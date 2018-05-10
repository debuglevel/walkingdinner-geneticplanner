import io.jenetics.EnumGene;
import io.jenetics.Genotype;
import io.jenetics.PermutationChromosome;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoursesProblem implements Problem<Courses, EnumGene<Team>, Double> {
    private final ISeq<Team> teams;

    public CoursesProblem(final ISeq<Team> teams) {
        this.teams = teams;
    }

    @Override
    public Codec<Courses, EnumGene<Team>> codec() {
        return Codec.of(
                Genotype.of(
                        PermutationChromosome.of(teams),
                        PermutationChromosome.of(teams),
                        PermutationChromosome.of(teams)),
                genotype-> new Courses(
                        genotype.getChromosome(0).toSeq().map(EnumGene::getAllele),
                        genotype.getChromosome(1).toSeq().map(EnumGene::getAllele),
                        genotype.getChromosome(2).toSeq().map(EnumGene::getAllele)
                )
        );
    }

    private double calculateFitness(Courses courses) {
        Set<Meeting> meetings = courses.toMeetings();

        Map<Team, Long> teamCookings = meetings.stream()
                .map(m -> m.getCookingTeam())
                .collect(Collectors.groupingBy(t -> t, Collectors.counting()));
        Long multipleCookings = teamCookings.entrySet().stream()
                .filter(kv -> kv.getValue() > 1)
                .map(kv -> kv.getValue())
                .collect(Collectors.summingLong(value -> value.longValue()));

        return multipleCookings.doubleValue();
    }

    private double calculateFitness(ISeq<Team> teams, String courseName) {
        Set<Meeting> meetings = Team.Companion.toMeetings(teams, courseName);

        double incompatibleTeams = meetings.stream()
                .filter(m -> m.areCompatibleTeams() == false)
                .collect(Collectors.counting())
                .doubleValue();

        return incompatibleTeams;
    }

    @Override
    public Function<Courses, Double> fitness() {
        return courses -> {
            return calculateFitness(courses.getCourse1teams(), "Vorspeise")
                    + calculateFitness(courses.getCourse2teams(), "Hauptgericht")
                    + calculateFitness(courses.getCourse3teams(), "Dessert")
                    + calculateFitness(courses);
        };
    }
}

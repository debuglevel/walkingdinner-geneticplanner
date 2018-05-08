import io.jenetics.EnumGene;
import io.jenetics.Genotype;
import io.jenetics.PermutationChromosome;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CoursesProblem implements Problem<CoursesProblem.Courses, EnumGene<Team>, Double> {
    private final ISeq<Team> _teams;

    public CoursesProblem(final ISeq<Team> teams) {
        _teams = teams;
    }

    final static class Courses {
        final ISeq<Team> course_1;
        final ISeq<Team> course_2;
        final ISeq<Team> course_3;

        Courses(final ISeq<Team> course1_teams, final ISeq<Team> course2_teams, final ISeq<Team> course3_teams) {
            course_1 = course1_teams;
            course_2 = course2_teams;
            course_3 = course3_teams;
        }

        @Override
        public String toString() {
            return String.format("course_1: %s - course_2: %s - course_3: %s", course_1, course_2, course_3);
        }
    }

    @Override
    public Codec<Courses, EnumGene<Team>> codec() {
        return Codec.of(
                Genotype.of(PermutationChromosome.of(_teams),
                        PermutationChromosome.of(_teams),
                        PermutationChromosome.of(_teams)),
                gt -> new Courses(
                        gt.getChromosome(0).toSeq().map(EnumGene::getAllele),
                        gt.getChromosome(1).toSeq().map(EnumGene::getAllele),
                        gt.getChromosome(2).toSeq().map(EnumGene::getAllele)
                )
//                gt -> gt.getChromosome().toSeq().map(EnumGene::getAllele)
        );

//        return Codecs.ofPermutation(_teams);
    }

    public Double fitness(ISeq<Team> teams, String name) {
            List<Meeting> meetings = new ArrayList<>();

            int mettingsCount = teams.length() / 3;
            for (int i = 0; i < mettingsCount; i++) {
                Team[] meetingTeams = {
                        teams.get(i * 3 + 0),
                        teams.get(i * 3 + 1),
                        teams.get(i * 3 + 2)
                };
                meetings.add(new Meeting(meetingTeams, name));
            }

            return meetings.stream()
                    .filter(m -> m.areCompatibleTeams() == false)
                    .collect(Collectors.counting())
                    .doubleValue();

    }

    @Override
    public Function<Courses, Double> fitness() {
        return courses -> {
            return fitness(courses.course_1, "Vorspeise")
                    + fitness(courses.course_2, "Hauptgericht")
                    + fitness(courses.course_3, "Dessert");
        };
    }
}

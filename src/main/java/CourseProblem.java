import io.jenetics.EnumGene;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;
import io.jenetics.engine.Codec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CourseProblem implements Problem<ISeq<Team>, EnumGene<Team>, Double> {
    private final ISeq<Team> _teams;

    public CourseProblem(final ISeq<Team> teams) {
        _teams = teams;
    }

    @Override
    public Codec<ISeq<Team>, EnumGene<Team>> codec() {
        return Codecs.ofPermutation(_teams);
    }

    @Override
    public Function<ISeq<Team>, Double> fitness() {
        return teams -> {
            List<Meeting> meetings = new ArrayList<>();

            int mettingsCount = teams.length() / 3;
            for (int i = 0; i < mettingsCount; i++) {
                Team[] meetingTeams = {teams.get(i*3+0), teams.get(i*3+1), teams.get(i*3+2)};
                meetings.add(new Meeting(meetingTeams, "x"));
            }

            return meetings.stream()
                    .filter(m -> m.areCompatibleTeams() == false)
                    .collect(Collectors.counting())
                    .doubleValue();
        };
    }
}

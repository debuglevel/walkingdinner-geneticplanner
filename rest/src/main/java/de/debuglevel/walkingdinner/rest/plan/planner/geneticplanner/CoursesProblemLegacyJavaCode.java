package de.debuglevel.walkingdinner.rest.plan.planner.geneticplanner;

import de.debuglevel.walkingdinner.rest.Meeting;
import de.debuglevel.walkingdinner.rest.participant.Team;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CoursesProblemLegacyJavaCode {
    public static double calculateMultipleCookingTeams(Set<Meeting> meetings) {
        Map<Team, Long> teamCookings = meetings.stream()
                .map(Meeting::getCookingTeam)
                .collect(
                        Collectors.groupingBy(
                                t -> t,
                                Collectors.counting()));

        Long countMultipleCookingTeams = teamCookings.entrySet().stream()
                .filter(kv -> kv.getValue() > 1)
                .map(Map.Entry::getValue)
                .mapToLong(value -> value)
                .sum();

        return countMultipleCookingTeams.doubleValue();
    }
}

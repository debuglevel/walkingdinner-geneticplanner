package de.debuglevel.walkingdinner.planners

import de.debuglevel.walkingdinner.planners.dietcompatibility.Diet
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class MeetingTests {
    @Test
    fun getCookingTeam() {
        // Arrange
        val teams = listOf(
            Team(UUID.randomUUID(), Diet.Omnivore, listOf(), Location(0.0, 0.0)),
            Team(UUID.randomUUID(), Diet.Omnivore, listOf(), Location(0.0, 0.0)),
            Team(UUID.randomUUID(), Diet.Omnivore, listOf(), Location(0.0, 0.0))
        )
        val meeting = Meeting("test", teams)

        // Act

        // Assert
        Assertions.assertThat(meeting.getCookingTeam()).isIn(teams)
    }
}
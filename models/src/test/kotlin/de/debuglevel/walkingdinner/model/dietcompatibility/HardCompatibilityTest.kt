package de.debuglevel.walkingdinner.model.dietcompatibility

import de.debuglevel.walkingdinner.model.Meeting
import de.debuglevel.walkingdinner.model.team.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HardCompatibilityTest {

    @ParameterizedTest
    @MethodSource("compatibleMeetingsProvider")
    fun `meetings are compatible`(testData: MeetingData) {
        // Arrange

        // Act
        val areCompatible = HardCompatibility.areCompatibleTeams(testData.meeting)

        // Assert
        assertThat(areCompatible).isTrue()
    }

    @ParameterizedTest
    @MethodSource("incompatibleMeetingsProvider")
    fun `meetings are incompatible`(testData: MeetingData) {
        // Arrange

        // Act
        val areCompatible = HardCompatibility.areCompatibleTeams(testData.meeting)

        // Assert
        assertThat(areCompatible).isFalse()
    }

    fun compatibleMeetingsProvider() = Stream.of(
            MeetingData(
                    Meeting(
                            listOf(
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Omnivore,
                                            listOf(),
                                            null
                                    ),
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Omnivore,
                                            listOf(),
                                            null
                                    ),
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Omnivore,
                                            listOf(),
                                            null
                                    )
                            ),
                            "course"
                    )
            ),
            MeetingData(
                    Meeting(
                            listOf(
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Vegan,
                                            listOf(),
                                            null
                                    ),
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Vegan,
                                            listOf(),
                                            null
                                    ),
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Vegan,
                                            listOf(),
                                            null
                                    )
                            ),
                            "course"
                    )
            ), MeetingData(
            Meeting(
                    listOf(
                            Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    "address",
                                    Diet.Vegetarisch,
                                    listOf(),
                                    null
                            ),
                            Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    "address",
                                    Diet.Vegetarisch,
                                    listOf(),
                                    null
                            ),
                            Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    "address",
                                    Diet.Vegetarisch,
                                    listOf(),
                                    null
                            )
                    ),
                    "course"
            )
    )
    )

    fun incompatibleMeetingsProvider() = Stream.of(
            MeetingData(
                    Meeting(
                            listOf(
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Vegetarisch,
                                            listOf(),
                                            null
                                    ),
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Omnivore,
                                            listOf(),
                                            null
                                    ),
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Omnivore,
                                            listOf(),
                                            null
                                    )
                            ),
                            "course"
                    )
            ),
            MeetingData(
                    Meeting(
                            listOf(
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Vegan,
                                            listOf(),
                                            null
                                    ),
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Vegan,
                                            listOf(),
                                            null
                                    ),
                                    Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                            "address",
                                            Diet.Vegetarisch,
                                            listOf(),
                                            null
                                    )
                            ),
                            "course"
                    )
            ), MeetingData(
            Meeting(
                    listOf(
                            Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    "address",
                                    Diet.Omnivore,
                                    listOf(),
                                    null
                            ),
                            Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    "address",
                                    Diet.Vegan,
                                    listOf(),
                                    null
                            ),
                            Team(Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                                    "address",
                                    Diet.Vegan,
                                    listOf(),
                                    null
                            )
                    ),
                    "course"
            )
    )
    )

    data class MeetingData(
            val meeting: Meeting
    )
}
package de.debuglevel.walkingdinner.model.dietcompatibility

import de.debuglevel.walkingdinner.model.Meeting
import de.debuglevel.walkingdinner.model.team.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseCompatibilityTest {

    @ParameterizedTest
    @MethodSource("compatibleMeetingsProvider")
    fun `meetings are compatible`(testData: MeetingData) {
        // Arrange

        // Act
        val areCompatible = CourseCompatibility.areCompatibleTeams(testData.meeting)

        // Assert
        assertThat(areCompatible).isTrue()
    }

    @ParameterizedTest
    @MethodSource("incompatibleMeetingsProvider")
    fun `meetings are incompatible`(testData: MeetingData) {
        // Arrange

        // Act
        val areCompatible = CourseCompatibility.areCompatibleTeams(testData.meeting)

        // Assert
        assertThat(areCompatible).isFalse()
    }

    fun compatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                listOf(
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Omnivore,
                        listOf(Capability.OmnivorVorspeise),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null
                    )
                ),
                "Vorspeise"
            )
        ),
        MeetingData(
            Meeting(
                listOf(
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegan,
                        listOf(Capability.VeganHauptgericht),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegetarisch,
                        listOf(),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null
                    )
                ),
                "Hauptspeise"
            )
        ), MeetingData(
            Meeting(
                listOf(
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegetarisch,
                        listOf(Capability.VeganDessert),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null
                    )
                ),
                "Dessert"
            )
        )
    )

    fun incompatibleMeetingsProvider() = Stream.of(
        MeetingData(
            Meeting(
                listOf(
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegetarisch,
                        listOf(Capability.VegetarischVorspeise),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Omnivore,
                        listOf(),
                        null
                    )
                ),
                "Vorspeise"
            )
        ),
        MeetingData(
            Meeting(
                listOf(
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Omnivore,
                        listOf(Capability.OmnivorHauptgericht, Capability.VegetarischHauptgericht),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegetarisch,
                        listOf(),
                        null
                    )
                ),
                "Hauptspeise"
            )
        ), MeetingData(
            Meeting(
                listOf(
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Omnivore,
                        listOf(Capability.VegetarischDessert),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null
                    ),
                    Team(
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        Cook(Name("cook"), MailAddress("mail"), PhoneNumber("123")),
                        "address",
                        Diet.Vegan,
                        listOf(),
                        null
                    )
                ),
                "Dessert"
            )
        )
    )

    data class MeetingData(
        val meeting: Meeting
    )
}
package de.debuglevel.walkingdinner.rest

import de.debuglevel.walkingdinner.rest.participant.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MeetingTests {
    fun `cooking team`() {
        // Arrange
        val cookingTeam = Team(
            null,
            Cook(
                name = Name(
                    name = "cook"
                ),
                mail = MailAddress(mail = "mail"),
                phoneNumber = PhoneNumber(number = "123")
            ),
            Cook(
                name = Name(
                    name = "cook"
                ),
                mail = MailAddress(mail = "mail"),
                phoneNumber = PhoneNumber(number = "123")
            ),
            "address",
            Diet.Omnivore,
            listOf(CookingCapability.OmnivoreAppetizer),
            null,
            "city"
        )
        val meeting = Meeting(
            listOf(
                cookingTeam,
                Team(
                    null,
                    Cook(
                        name = Name(
                            name = "cook"
                        ),
                        mail = MailAddress(mail = "mail"),
                        phoneNumber = PhoneNumber(number = "123")
                    ),
                    Cook(
                        name = Name(
                            name = "cook"
                        ),
                        mail = MailAddress(mail = "mail"),
                        phoneNumber = PhoneNumber(number = "123")
                    ),
                    "address",
                    Diet.Omnivore,
                    listOf(),
                    null,
                    "city"
                ),
                Team(
                    null,
                    Cook(
                        name = Name(
                            name = "cook"
                        ),
                        mail = MailAddress(mail = "mail"),
                        phoneNumber = PhoneNumber(number = "123")
                    ),
                    Cook(
                        name = Name(
                            name = "cook"
                        ),
                        mail = MailAddress(mail = "mail"),
                        phoneNumber = PhoneNumber(number = "123")
                    ),
                    "address",
                    Diet.Omnivore,
                    listOf(),
                    null,
                    "city"
                )
            ),
            "Vorspeise"
        )

        // Act

        // Assert
        Assertions.assertThat(meeting.getCookingTeam()).isEqualTo(cookingTeam)
    }
}
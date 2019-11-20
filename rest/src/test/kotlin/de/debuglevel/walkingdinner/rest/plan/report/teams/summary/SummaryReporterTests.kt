package de.debuglevel.walkingdinner.rest.plan.report.teams.summary

import de.debuglevel.walkingdinner.rest.MailAddress
import de.debuglevel.walkingdinner.rest.Meeting
import de.debuglevel.walkingdinner.rest.PhoneNumber
import de.debuglevel.walkingdinner.rest.participant.*
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SummaryReporterTests {

    @Inject
    lateinit var summaryReporter: SummaryReporter

    private val meeting: Meeting
        get() {
            val cookingTeam = Team(
                null,
                Cook(
                    name = Name(
                        name = "cook1"
                    ),
                    mail = MailAddress(mail = "mail"),
                    phoneNumber = PhoneNumber(number = "123")
                ),
                Cook(
                    name = Name(
                        name = "cook2"
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
                                name = "cook3"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook4"
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
                                name = "cook5"
                            ),
                            mail = MailAddress(mail = "mail"),
                            phoneNumber = PhoneNumber(number = "123")
                        ),
                        Cook(
                            name = Name(
                                name = "cook6"
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

            return meeting
        }

    @Test
    fun `meeting to string`() {
        // Arrange

        // Act
        val report = summaryReporter.meetingToString(meeting)

        // Assert
        val assertReport = "[cook1 & cook2]\tcook3 & cook4\tcook5 & cook6"
        Assertions.assertThat(report).isEqualTo(assertReport)
    }

    @Test
    fun `generate report`() {
        // Arrange

        // Act
        val report = summaryReporter.generateReports(setOf(meeting))

        // Assert
        val assertReport = "== Course Vorspeise\n[cook1 & cook2]\tcook3 & cook4\tcook5 & cook6"
        Assertions.assertThat(report).isEqualTo(assertReport)
    }
}
package de.debuglevel.walkingdinner.rest.participant

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MailAddressTests {

    @ParameterizedTest
    @MethodSource("validMailProvider")
    fun `toString() equals mail`(testData: MailData) {
        // Arrange

        // Act
        val mail = MailAddress(testData.mail)

        // Assert
        assertThat(mail.toString()).isEqualTo(testData.mail)
    }

    @ParameterizedTest
    @MethodSource("validMailProvider")
    fun `valid mails can be created`(testData: MailData) {
        // Arrange

        // Act && Assert
        assertDoesNotThrow { MailAddress(testData.mail) }
    }

    fun validMailProvider() = Stream.of(
        MailData(mail = "email@example.com"),
        MailData(mail = "firstname.lastname@example.com"),
        MailData(mail = "email@subdomain.example.com"),
        MailData(mail = "firstname+lastname@example.com"),
        MailData(mail = "email@123.123.123.123"),
        MailData(mail = "email@[123.123.123.123]"),
        MailData(mail = "\"email\"@example.com"),
        MailData(mail = "1234567890@example.com"),
        MailData(mail = "email@example-one.com"),
        MailData(mail = "_______@example.com"),
        MailData(mail = "email@example.name"),
        MailData(mail = "email@example.museum"),
        MailData(mail = "email@example.co.jp"),
        MailData(mail = "firstname-lastname@example.com")
    )

    fun invalidMailProvider() = Stream.of(
        MailData(mail = "plainaddress"),
        MailData(mail = "#@%^%#$@#$@#.com"),
        MailData(mail = "@example.com"),
        MailData(mail = "Joe Smith <email@example.com>"),
        MailData(mail = "email.example.com"),
        MailData(mail = "email@example@example.com"),
        MailData(mail = ".email@example.com"),
        MailData(mail = "email.@example.com"),
        MailData(mail = "email..email@example.com"),
        MailData(mail = "あいうえお@example.com"),
        MailData(mail = "email@example.com (Joe Smith)"),
        MailData(mail = "email@example"),
        MailData(mail = "email@-example.com"),
        MailData(mail = "email@example.web"),
        MailData(mail = "email@111.222.333.44444"),
        MailData(mail = "email@example..com"),
        MailData(mail = "Abc..123@example.com")
    )

    data class MailData(
        val mail: String
    )
}
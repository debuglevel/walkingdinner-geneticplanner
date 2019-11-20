package de.debuglevel.walkingdinner.rest.participant

import de.debuglevel.walkingdinner.rest.PhoneNumber
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PhoneNumberTests {

    @Test
    fun `phonenumber is formatted`() {
        // Arrange

        // Act
        val formattedPhonenumber = PhoneNumber(number = "0951123456")

        // Assert
        Assertions.assertThat(formattedPhonenumber.toString()).isEqualTo("+49 951 123456")
    }
}
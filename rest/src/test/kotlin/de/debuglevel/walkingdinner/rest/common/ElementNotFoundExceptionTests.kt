package de.debuglevel.walkingdinner.rest.common

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ElementNotFoundExceptionTests {
    @Test
    fun `contains ID`() {
        // Arrange
        val uuidString = "ebac83b0-a12a-4b2b-822a-db561fa4ae54"
        val uuid = UUID.fromString(uuidString)

        // Act
        val elementNotFoundException = ElementNotFoundException(uuid)

        // Assert
        Assertions.assertThat(elementNotFoundException.toString()).contains(uuidString)
    }
}

package de.debuglevel.walkingdinner.rest.common

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Base64StringTests {

    fun base64Provider() = Stream.of(
        Base64Data(base64 = "SGFsbG8gV2VsdA==", decoded = "Hallo Welt")
    )

    @ParameterizedTest
    @MethodSource("base64Provider")
    fun `original value is retained`(testData: Base64Data) {
        // Arrange

        // Act
        val base64string = Base64String(testData.base64)

        // Assert
        Assertions.assertThat(base64string.value).isEqualTo(testData.base64)
    }

    @ParameterizedTest
    @MethodSource("base64Provider")
    fun `converts to plain text`(testData: Base64Data) {
        // Arrange

        // Act
        val base64string = Base64String(testData.base64)
        //val base64Strings = strings.keys.map { Base64String(it) }

        // Assert
        Assertions.assertThat(base64string.asString).isEqualTo(testData.decoded)
    }

    data class Base64Data(
        val base64: String,
        val decoded: String
    )
}
package de.debuglevel.walkingdinner.rest.participant


import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NameTests {

    @ParameterizedTest
    @MethodSource("nameProvider")
    fun `get firstname`(testData: NameData) {
        // Arrange

        // Act
        val name = Name(name = testData.name)

        // Assert
        Assertions.assertThat(name.firstname).isEqualTo(testData.expectedFirstname)
    }

    @ParameterizedTest
    @MethodSource("nameProvider")
    fun `get lastname`(testData: NameData) {
        // Arrange

        // Act
        val name = Name(name = testData.name)

        // Assert
        Assertions.assertThat(name.lastname).isEqualTo(testData.expectedLastname)
    }

    @ParameterizedTest
    @MethodSource("nameProvider")
    fun getAbbreviatedName(testData: NameData) {
        // Arrange

        // Act
        val name = Name(name = testData.name)

        // Assert
        Assertions.assertThat(name.abbreviatedName).isEqualTo(testData.expectedAbbreviatedName)
    }

    fun nameProvider() = Stream.of(
        NameData(
            name = "John Dorian",
            expectedFirstname = "John",
            expectedLastname = "Dorian",
            expectedAbbreviatedName = "J. Dorian"
        ),
        NameData(
            name = "Jennifer Dylan Cox",
            expectedFirstname = "Jennifer",
            expectedLastname = "Cox",
            expectedAbbreviatedName = "J. Cox"
        ),
        NameData(
            name = "Robert-Bob Kelso",
            expectedFirstname = "Robert-Bob",
            expectedLastname = "Kelso",
            expectedAbbreviatedName = "R. Kelso"
        )
    )

    data class NameData(
        val name: String,
        val expectedFirstname: String,
        val expectedLastname: String,
        val expectedAbbreviatedName: String
    )
}
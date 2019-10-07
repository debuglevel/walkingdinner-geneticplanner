package de.debuglevel.walkingdinner.planners.dietcompatibility

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HardCompatibilityTests {

    @ParameterizedTest
    @MethodSource("compatibleMeetingsProvider")
    fun `meetings are compatible`(testData: TestDataHardCompatibility.MeetingData) {
        // Arrange

        // Act
        val areCompatible = HardDietCompatibility.areCompatibleTeams(testData.meeting)

        // Assert
        assertThat(areCompatible).isTrue()
    }

    @ParameterizedTest
    @MethodSource("incompatibleMeetingsProvider")
    fun `meetings are incompatible`(testData: TestDataHardCompatibility.MeetingData) {
        // Arrange

        // Act
        val areCompatible = HardDietCompatibility.areCompatibleTeams(testData.meeting)

        // Assert
        assertThat(areCompatible).isFalse()
    }

    fun compatibleMeetingsProvider() = TestDataHardCompatibility.compatibleMeetingsProvider()
    fun incompatibleMeetingsProvider() = TestDataHardCompatibility.incompatibleMeetingsProvider()
}
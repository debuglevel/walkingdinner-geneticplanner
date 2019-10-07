package de.debuglevel.walkingdinner.planners.dietcompatibility

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseCompatibilityTests {

    @ParameterizedTest
    @MethodSource("compatibleMeetingsProvider")
    fun `meetings are compatible`(testData: TestDataCourseCompatibility.MeetingData) {
        // Arrange

        // Act
        val areCompatible = CourseDietCompatibility.areCompatibleTeams(testData.meeting)

        // Assert
        assertThat(areCompatible).isTrue()
    }

    @ParameterizedTest
    @MethodSource("incompatibleMeetingsProvider")
    fun `meetings are incompatible`(testData: TestDataCourseCompatibility.MeetingData) {
        // Arrange

        // Act
        val areCompatible = CourseDietCompatibility.areCompatibleTeams(testData.meeting)

        // Assert
        assertThat(areCompatible).isFalse()
    }

    fun compatibleMeetingsProvider() = TestDataCourseCompatibility.compatibleMeetingsProvider()
    fun incompatibleMeetingsProvider() = TestDataCourseCompatibility.incompatibleMeetingsProvider()
}
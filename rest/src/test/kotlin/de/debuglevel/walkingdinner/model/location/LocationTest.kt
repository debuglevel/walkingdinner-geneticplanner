package de.debuglevel.walkingdinner.model.location

import org.assertj.core.api.Assertions

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocationTest {

    @ParameterizedTest
    @MethodSource("locationProvider")
    fun `calculate distance`(testData: LocationDistanceData) {
        // Arrange

        // Act
        val distance1to2 = testData.location1.calculateDistance(testData.location2)
        val distance2to1 = testData.location2.calculateDistance(testData.location1)

        // Assert
        Assertions.assertThat(distance1to2).isCloseTo(testData.distance, Assertions.within(0.01))
        Assertions.assertThat(distance2to1).isEqualTo(testData.distance, Assertions.within(0.01))
    }

    fun locationProvider() = Stream.of(
        LocationDistanceData(
            Location("Markusplatz 3", 10.883799, 49.895679),
            Location("Markusplatz 3", 10.883799, 49.895679),
            0.0
        ),
        LocationDistanceData(
            Location("Markusplatz 3", 10.883799, 49.895679),
            Location("Eisgrube", 10.885178, 49.889114),
            0.74
        )
    )

    data class LocationDistanceData(
        val location1: Location,
        val location2: Location,
        val distance: Double
    )
}
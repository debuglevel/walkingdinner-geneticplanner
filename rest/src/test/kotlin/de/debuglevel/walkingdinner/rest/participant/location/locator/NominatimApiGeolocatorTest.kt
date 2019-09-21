package de.debuglevel.walkingdinner.rest.participant.location.locator

import de.debuglevel.walkingdinner.rest.participant.location.Location
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NominatimApiGeolocatorTest {

    @ParameterizedTest
    @MethodSource("locationProvider")
    fun `get location for address`(testData: LocationData) {
        // Arrange
        val nominatimApiGeolocator = NominatimApiGeolocator("Bamberg")

        // Act
        val location = nominatimApiGeolocator.getLocation(testData.address)

        // Assert
        assertThat(location.lat).isEqualTo(testData.expectedLocation.lat, within(0.000001))
        assertThat(location.lng).isEqualTo(testData.expectedLocation.lng, within(0.000001))
    }

    fun locationProvider() = Stream.of(
        LocationData("Markusplatz 3", Location("Bamberg Markusplatz 3", 10.883799, 49.895679)),
        LocationData("Eisgrube 1", Location("Bamberg Eisgrube", 10.885329, 49.889126))
    )

    data class LocationData(
        val address: String,
        val expectedLocation: Location
    )
}
package de.debuglevel.walkingdinner.rest.participant.location.locator

import de.debuglevel.walkingdinner.rest.participant.location.Location
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NominatimApiGeolocatorTests {
    private val about11meters = 0.0001

    @Inject
    lateinit var nominatimApiGeolocator: NominatimApiGeolocator

    @ParameterizedTest
    @MethodSource("locationProvider")
    fun `get location for address`(testData: LocationData) {
        // Arrange

        // Act
        val location = nominatimApiGeolocator.getLocation(testData.address, testData.city)

        // Assert
        assertThat(location.lat).isEqualTo(testData.expectedLocation.lat, within(about11meters))
        assertThat(location.lng).isEqualTo(testData.expectedLocation.lng, within(about11meters))
    }

    fun locationProvider() = Stream.of(
        LocationData("Markusplatz 3", "Bamberg", Location("Bamberg Markusplatz 3", 10.883799, 49.895679)),
        LocationData("Eisgrube 1", "Bamberg", Location("Bamberg Eisgrube", 10.885329, 49.889126))
    )

    data class LocationData(
        val address: String,
        val city: String,
        val expectedLocation: Location
    )
}
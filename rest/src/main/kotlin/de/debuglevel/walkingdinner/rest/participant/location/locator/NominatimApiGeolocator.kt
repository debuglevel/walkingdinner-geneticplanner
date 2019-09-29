package de.debuglevel.walkingdinner.rest.participant.location.locator

import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.participant.location.GeoUtils
import de.debuglevel.walkingdinner.rest.participant.location.Location
import fr.dudie.nominatim.client.JsonNominatimClient
import fr.dudie.nominatim.client.request.NominatimSearchRequest
import fr.dudie.nominatim.model.Address
import mu.KotlinLogging
import org.apache.http.impl.client.HttpClientBuilder
import java.text.DecimalFormat
import javax.inject.Singleton
import kotlin.concurrent.withLock

@Singleton
class NominatimApiGeolocator : Geolocator {
    private val logger = KotlinLogging.logger {}

    private val singleRequestLock = java.util.concurrent.locks.ReentrantLock()

    private val nominatimClient: JsonNominatimClient
        get() = buildNominatimClient()

    init {
        logger.debug { "Initializing NominatimApiGeolocator..." }
    }

    override fun getLocation(address: String?, city: String): Location {
        val cityAddress = if (address.isNullOrBlank()) city else "$address, $city"
        val result = try {
            getNominatimAddress(cityAddress)
        } catch (e: NoAddressesFoundException) {
            getNominatimAddress(city)
        }

        return Location(
            cityAddress,
            result.longitude,
            result.latitude
        )
    }

    override fun initializeTeamLocation(team: Team) {
        logger.debug("Geo-locating team '$team' by Nominatim API...")

        val location = getLocation(team.address, team.city)
        team.location = location

        val cityLocation = getLocation(null, team.city)

        val distanceToCity = GeoUtils.calculateDistanceInKilometer(cityLocation, location)

        logger.debug("Geo-located team '$team' ${DecimalFormat("#.##").format(distanceToCity)}km from center by Nominatim API")
    }

    private fun buildNominatimClient(): JsonNominatimClient {
        val httpClient = HttpClientBuilder.create().build()

        val baseUrl = "https://nominatim.openstreetmap.org/"
        val email = "debuglevel.de"
        return JsonNominatimClient(baseUrl, httpClient, email)
    }

    private fun getNominatimAddress(cityAddress: String): Address {
        logger.debug("Searching address '$cityAddress'...")

        val searchRequest = NominatimSearchRequest()
        searchRequest.setQuery(cityAddress)

        // OpenStreetMaps Nominatim API allows only 1 concurrent connection. Ensure this with a lock.
        logger.debug("Waiting for lock to call NominatimClient for address '$cityAddress'...")
        val addresses = singleRequestLock.withLock {
            logger.debug("Calling NominatimClient for address '$cityAddress'...")
            val addresses = nominatimClient.search(searchRequest)
            logger.debug("Called NominatimClient for address '$cityAddress': ${addresses.size} results.")
            addresses
        }

        if (addresses.isEmpty()) {
            logger.warn { "No address found for '$cityAddress'" }
            throw NoAddressesFoundException(cityAddress)
        }

        val result = addresses[0]

        logger.debug("Searching address '$cityAddress': ${result.displayName}")

        // TODO: if available, prefer class="building"
        return result
    }

    class NoAddressesFoundException(address: String) :
        Exception("No Nominatim API results found for address '$address'")
}
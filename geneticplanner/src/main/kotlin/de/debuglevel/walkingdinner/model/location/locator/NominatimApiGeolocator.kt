package de.debuglevel.walkingdinner.model.location.locator

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import de.debuglevel.walkingdinner.model.location.GeoUtils
import de.debuglevel.walkingdinner.model.location.Location
import de.debuglevel.walkingdinner.model.team.Team
import java.text.DecimalFormat
import fr.dudie.nominatim.client.request.NominatimSearchRequest
import java.io.IOException
import fr.dudie.nominatim.client.JsonNominatimClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.conn.SingleClientConnManager
import org.apache.http.conn.ClientConnectionManager
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.scheme.SchemeRegistry
import com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER
import fr.dudie.nominatim.model.Address
import mu.KotlinLogging
import org.apache.http.conn.ssl.SSLSocketFactory
import java.util.concurrent.locks.Lock
import kotlin.concurrent.withLock


class NominatimApiGeolocator(private val city: String) : Geolocator {
    private val logger = KotlinLogging.logger {}

    private val singleRequestLock = java.util.concurrent.locks.ReentrantLock()

    private var nominatimClient = buildNominatimClient()

    private val cityLocation: Location

    override fun getLocation(address: String): Location {
        val result = getNominatimAddress("$address, $city")

        return Location(
                address,
                result.longitude,
                result.latitude)
    }

    override fun initializeTeamLocation(team: Team) {
        logger.debug("Geo-locating team '$team' by Nominatim API...")

        val location = getLocation(team.address)
        team.location = location

        val distanceToCity = GeoUtils.calculateDistanceInKilometer(cityLocation, location)

        logger.debug("Geo-located team '$team' ${DecimalFormat("#.##").format(distanceToCity)}km from center by Nominatim API")
    }

    init {
        logger.debug { "Initializing NominatimApiGeolocator..." }
        val result = getNominatimAddress(city)
        cityLocation = Location(
                city,
                result.longitude,
                result.latitude)
    }

    private fun buildNominatimClient(): JsonNominatimClient {
        val registry = SchemeRegistry()
        registry.register(Scheme("https", SSLSocketFactory.getSocketFactory(), 443))
        val connexionManager = SingleClientConnManager(null, registry)

        val httpClient = DefaultHttpClient(connexionManager, null)

        val baseUrl = "https://nominatim.openstreetmap.org/"
        val email = "debuglevel.de"
        return JsonNominatimClient(baseUrl, httpClient, email)
    }

    fun getNominatimAddress(address: String): Address {
        logger.debug("Searching address '$address' ...")

        val r = NominatimSearchRequest()
        r.setQuery(address)

        // OpenStreetMaps Nominatim API allows only 1 concurrent connection. Ensure this with a lock.
        logger.debug("Waiting for lock to call NominatimClient for address '$address'...")
        val addresses = singleRequestLock.withLock {
            logger.debug("Calling NominatimClient for address '$address'...")
            val addresses = nominatimClient.search(r)
            logger.debug("Calling NominatimClient for address '$address' done. Found ${addresses.size} results.")
            addresses
        }

        if (addresses.isEmpty())
        {
            logger.warn { "No address found for '$address'; using town center instead." }
            return getNominatimAddress(city)
            //throw NoAddressesFound(address)
        }

        val result = addresses[0]
        logger.debug("Using address: ${result.displayName}")

        // TODO: if available, prefer class="building"
        return result
    }

    class NoAddressesFound(address: String) : Exception("No Nominatim API results found for address '$address'")
}
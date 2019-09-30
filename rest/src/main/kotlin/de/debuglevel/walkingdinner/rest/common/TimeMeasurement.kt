package de.debuglevel.walkingdinner.rest.common

import mu.KotlinLogging
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.roundToInt

object TimeMeasurement {
    private val logger = KotlinLogging.logger {}

    private val measurements = mutableMapOf<Any, Measurement>()

    /**
     * Remarks:
     * - use only single thread
     * - only measure one variant of a function; replace it afterwards and recompile and rerun (subsequent calls are faster than the first one)
     */
    fun add(
        id: Any,
        nanosecondsDuration: Long,
        reportStep: Long
    ) {
        val measurement = measurements.putIfAbsent(id, Measurement(id))
        if (measurement != null) {
            val callsSum = measurement.calls.incrementAndGet()
            val nanosecondsSum = measurement.nanoseconds.addAndGet(nanosecondsDuration)

            if (callsSum % reportStep == 0L) {
                val durationPerCall = nanosecondsSum / callsSum
                val callsPerSecond = (callsSum / (nanosecondsSum / 1_000_000_000.0)).roundToInt()

                println("Performance of '${measurement.id}' after $callsSum calls: $durationPerCall ns/call or $callsPerSecond calls/s")
            }
        }
    }

    data class Measurement(val id: Any) {
        var calls = AtomicLong(0)
        var nanoseconds = AtomicLong(0)
    }
}
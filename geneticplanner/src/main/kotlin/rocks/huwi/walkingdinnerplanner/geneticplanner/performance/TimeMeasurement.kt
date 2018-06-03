package rocks.huwi.walkingdinnerplanner.geneticplanner.performance

import java.util.concurrent.atomic.AtomicLong

object TimeMeasurement {
    private val measurements = mutableMapOf<Any, Measurement>()

    /**
     * Remarks:
     * - use only single thread
     * - only measure one variant of a function; replace it afterwards and recompile and rerun (subsequent calls are faster than the first one)
     */
    fun add(id: Any,
            nanoseconds: Long,
            reportStep: Long = 10_000_000) {
        val measurement = measurements.putIfAbsent(id, Measurement(id))
        if (measurement != null) {
            val calls = measurement.calls.incrementAndGet()
            val nanoseconds = measurement.nanoseconds.addAndGet(nanoseconds)

            if (calls % reportStep == 0L) {
                println("Performance of ${measurement.id} after $calls Calls = ${nanoseconds / calls} ns/call or ${Math.round(calls / (nanoseconds / 1_000_000_000.0))} calls/s")
            }
        }
    }

    data class Measurement(val id: Any) {
        var calls = AtomicLong(0)
        var nanoseconds = AtomicLong(0)
    }
}
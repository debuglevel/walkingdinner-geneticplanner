package de.debuglevel.walkingdinner.rest.responsetransformer

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import mu.KotlinLogging
import org.litote.kmongo.Id
import org.litote.kmongo.toId
import spark.ResponseTransformer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Transformer which converts objects to JSON
 * Note: the responseTransformer parameter will be removed in Spark Kotlin and must be called explicitly.
 */
object JsonTransformer : ResponseTransformer {
    private val logger = KotlinLogging.logger {}

    private val gson = GsonBuilder()
        .registerTypeAdapter(
            Id::class.java,
            JsonSerializer<Id<Any>> { id, _, _ -> JsonPrimitive(id?.toString()) })
        .registerTypeAdapter(
            Id::class.java,
            JsonDeserializer<Id<Any>> { id, _, _ -> id.asString.toId() })
        .registerTypeAdapter(
            LocalDateTime::class.java,
            JsonSerializer<LocalDateTime> { localDateTime, _, _ ->
                JsonPrimitive(
                    localDateTime?.format(
                        DateTimeFormatter.ofPattern(
                            "EEEE, dd.MM.yyyy HH:mm"
                        )
                    )
                )
            })
        .setPrettyPrinting()
        .create()

    override fun render(model: Any?): String {
        return gson.toJson(model)
    }
}
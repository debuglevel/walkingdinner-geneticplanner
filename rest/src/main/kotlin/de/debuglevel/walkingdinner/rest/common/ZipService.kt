package de.debuglevel.walkingdinner.rest.common

import mu.KotlinLogging
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.inject.Singleton

@Singleton
class ZipService {
    private val logger = KotlinLogging.logger {}

    data class ZipItem(val filename: String, val inputStream: InputStream)

    fun zip(zipItems: Set<ZipItem>, zipOutputStream: OutputStream) {
        logger.debug { "Creating zip file..." }

        ZipOutputStream(BufferedOutputStream(zipOutputStream)).use { outputStream ->
            for (zipItem in zipItems) {
                zipItem.inputStream.use { inputStream ->
                    BufferedInputStream(inputStream).use { bufferedInputStream ->
                        logger.trace { "Adding zip entry with filename '${zipItem.filename}'..." }

                        val zipEntry = ZipEntry(zipItem.filename)
                        outputStream.putNextEntry(zipEntry)
                        bufferedInputStream.copyTo(outputStream, 1024)

                        logger.trace { "Added zip entry with filename '${zipItem.filename}'" }
                    }
                }
            }
        }

        logger.debug { "Created zip file" }
    }
}
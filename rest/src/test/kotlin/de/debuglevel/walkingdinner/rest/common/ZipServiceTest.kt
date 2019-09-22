package de.debuglevel.walkingdinner.rest.common

import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ZipServiceTests {

    @Inject
    lateinit var zipService: ZipService

    @Test
    fun `zip files`() {
        // Arrange
        val zipItems = setOf(
            ZipService.ZipItem("file1.txt", "content of file1.txt".byteInputStream()),
            ZipService.ZipItem("file2.txt", "content of file2.txt".byteInputStream()),
            ZipService.ZipItem("file3.txt", "content of file3.txt".byteInputStream())
        )

        // Act
        val outputStream = ByteArrayOutputStream()
        //val outputStream = FileOutputStream("test.zip")
        zipService.zip(zipItems, outputStream)
        //val bytes = outputStream.toByteArray()

        // Assert
        // TODO: test the zip file if the files with content really exist
//        val byteStream = ByteArrayInputStream(bytes);
//        val zipStream = ZipInputStream(byteStream);
    }
}
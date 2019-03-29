package net.netau.vasyoid.command

import org.junit.Assert.*
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

class ExternalCommandTest {

    @Test
    fun dirLs() {
        val files = File("./")
            .listFiles()
            .joinToString("\n") { it.name }
        val arguments = if (System.getProperty("os.name").startsWith("Windows")) {
            listOf("dir", "/B", "/ON")
        } else {
            listOf("ls", "-1AU")
        }
        val outputStream = ByteArrayOutputStream()
        assertTrue(
            ExternalCommand(
                ByteArrayInputStream(byteArrayOf()).bufferedReader(),
                arguments,
                outputStream.bufferedWriter()
            ).run()
        )
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        assertEquals(files + "\n", outputString.replace("\r\n", "\n"))
    }

    @Test
    fun unknownCommand() {
        val arguments = listOf("unknowncommandthatdoesnotexist")
        assertFalse(
            ExternalCommand(
                ByteArrayInputStream(byteArrayOf()).bufferedReader(),
                arguments,
                System.out.bufferedWriter()
            ).run()
        )
    }
}
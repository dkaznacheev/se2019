package net.netau.vasyoid.command

import org.junit.Assert
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class EchoTest {

    @Test
    fun echo() {
        val arguments = listOf("asd", "fsd    sa", "$|ASD")
        val outputStream = ByteArrayOutputStream()
        Assert.assertTrue(Echo(System.`in`.bufferedReader(), arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        Assert.assertEquals(
            arguments.joinToString(" ") + System.lineSeparator(),
            outputString
        )
    }
}

package net.netau.vasyoid.command

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class WcTest {

    @Rule
    @JvmField
    val tmpFolder = TemporaryFolder()

    private val stdin = System.`in`.bufferedReader()
    private val inputString = "asd\nfds sdf\n123"
    private val fileName = "filename"
    private val stat = "2 4 15 "

    @Test
    fun wcFromInput() {
        val arguments = listOf<String>()
        val input = ByteArrayInputStream(inputString.toByteArray()).bufferedReader()
        val outputStream = ByteArrayOutputStream()
        assertTrue(Wc(input, arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        assertEquals(stat + System.lineSeparator(), outputString)
    }

    @Test
    fun catFromFile() {
        val file = tmpFolder.newFile(fileName)
        val arguments = listOf<String>(file.canonicalPath)
        file.writeText(inputString)
        val outputStream = ByteArrayOutputStream()
        assertTrue(Wc(stdin, arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        assertEquals(stat + file.canonicalPath + System.lineSeparator(), outputString)
    }

    @Test
    fun catFromTwoFilesFile() {
        val file = tmpFolder.newFile(fileName)
        val arguments = listOf<String>(file.canonicalPath, file.canonicalPath)
        file.writeText(inputString)
        val outputStream = ByteArrayOutputStream()
        assertTrue(Wc(stdin, arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        var expected = stat + file.canonicalPath + System.lineSeparator()
        expected = expected + expected + "4 8 30 total" + System.lineSeparator()
        assertEquals(expected, outputString)
    }

    @Test
    fun nonExistingFile() {
        val arguments = listOf("filethatdoesnotexist")
        assertFalse(Cat(stdin, arguments, System.out.bufferedWriter()).run())
    }

}
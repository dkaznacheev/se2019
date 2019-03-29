package net.netau.vasyoid.command

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class CatTest {

    @Rule
    @JvmField
    val tmpFolder = TemporaryFolder()

    private val stdin = System.`in`.bufferedReader()
    private val inputString = "asd\nfds\n123"
    private val fileName = "filename"

    @Test
    fun catFromInput() {
        val arguments = listOf<String>()
        val input = ByteArrayInputStream(inputString.toByteArray()).bufferedReader()
        val outputStream = ByteArrayOutputStream()
        assertTrue(Cat(input, arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        assertEquals(inputString, outputString)
    }

    @Test
    fun catFromFile() {
        val file = tmpFolder.newFile(fileName)
        val arguments = listOf<String>(file.canonicalPath)
        file.writeText(inputString)
        val outputStream = ByteArrayOutputStream()
        assertTrue(Cat(stdin, arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        assertEquals(inputString, outputString)
    }

    @Test
    fun nonExistingFile() {
        val arguments = listOf("filethatdoesnotexist")
        assertFalse(Cat(stdin, arguments, System.out.bufferedWriter()).run())
    }
}

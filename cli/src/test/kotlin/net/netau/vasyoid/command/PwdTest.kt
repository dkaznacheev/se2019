package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

class PwdTest {

    @Before
    fun setCd() {
        VariablesStorage.set("PWD", System.getProperty("user.dir"))
    }

    @Test
    fun pwd() {
        val arguments = listOf<String>()
        val outputStream = ByteArrayOutputStream()
        assertTrue(Pwd(System.`in`.bufferedReader(), arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        assertEquals(File("./").canonicalPath + System.lineSeparator(), outputString)
    }
}
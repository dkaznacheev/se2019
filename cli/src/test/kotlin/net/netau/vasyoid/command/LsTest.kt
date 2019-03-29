package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File


class LsTest {

    @Rule
    @JvmField
    val tmpFolder = TemporaryFolder()

    private val folderName = "testFolder"
    private val fileName = "tmpFile"
    private val stdin = System.`in`.bufferedReader()

    @Before
    fun setCd() {
        VariablesStorage.set("PWD", System.getProperty("user.dir"))
    }

    @Test
    fun lsNoArgs() {
        val input = ByteArrayInputStream("".toByteArray()).bufferedReader()
        val outputStream = ByteArrayOutputStream()

        tmpFolder.newFile(fileName)

        VariablesStorage.set("PWD", tmpFolder.root.canonicalPath)
        Assert.assertTrue(Ls(input, listOf(), outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        Assert.assertEquals(fileName + System.lineSeparator(), outputString)
    }

    @Test
    fun lsWithArgs() {
        val input = ByteArrayInputStream("".toByteArray()).bufferedReader()
        val outputStream = ByteArrayOutputStream()

        val folder = tmpFolder.newFolder(folderName)
        val tempFile = File.createTempFile(fileName, "", folder)

        VariablesStorage.set("PWD", tmpFolder.root.canonicalPath)
        Assert.assertTrue(Ls(input, listOf(folderName), outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        Assert.assertEquals(tempFile.name + System.lineSeparator(), outputString)
    }

    @Test
    fun lsWithArgsAbsolutePath() {
        val input = ByteArrayInputStream("".toByteArray()).bufferedReader()
        val outputStream = ByteArrayOutputStream()

        val folder = tmpFolder.newFolder(folderName)
        val tempFile = File.createTempFile(fileName, "", folder)

        Assert.assertTrue(Ls(input, listOf(folder.canonicalPath), outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        Assert.assertEquals(tempFile.name + System.lineSeparator(), outputString)
    }

    @Test
    fun lsNoFolder() {
        val input = ByteArrayInputStream("".toByteArray()).bufferedReader()
        val outputStream = ByteArrayOutputStream()

        VariablesStorage.set("PWD", tmpFolder.root.canonicalPath)
        Assert.assertFalse(Ls(input, listOf("NoSuchFolder"), outputStream.bufferedWriter()).run())
    }
}
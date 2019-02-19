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


class CdTest {

    @Rule
    @JvmField
    val tmpFolder = TemporaryFolder()

    private val folderName = "testFolder"
    private val stdin = System.`in`.bufferedReader()

    @Before
    fun setCd() {
        VariablesStorage.set("PWD", System.getProperty("user.dir"))
    }

    @Test
    fun cdNoArgs() {
        val arguments = listOf<String>()
        val input = ByteArrayInputStream("".toByteArray()).bufferedReader()
        val outputStream = ByteArrayOutputStream()
        Assert.assertEquals(System.getProperty("user.dir"), VariablesStorage.get("PWD"))
        Assert.assertTrue(Cd(input, arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        Assert.assertEquals("", outputString)
        Assert.assertEquals(System.getProperty("user.dir"), VariablesStorage.get("PWD"))
    }

    @Test
    fun cdArgs() {
        val path = tmpFolder.root.canonicalPath
        val arguments = listOf<String>(path)
        val outputStream = ByteArrayOutputStream()
        Assert.assertTrue(Cd(stdin, arguments, outputStream.bufferedWriter()).run())
        Assert.assertEquals(path, VariablesStorage.get("PWD"))
    }

    @Test
    fun cdUp() {
        val folder = tmpFolder.newFolder(folderName)
        val path = tmpFolder.root.canonicalPath
        val folderPath = folder.canonicalPath

        val outputStream = ByteArrayOutputStream()
        Assert.assertTrue(Cd(stdin, listOf<String>(folderPath), outputStream.bufferedWriter()).run())
        Assert.assertEquals(folderPath, VariablesStorage.get("PWD"))

        Assert.assertTrue(Cd(stdin, listOf<String>(".."), outputStream.bufferedWriter()).run())
        Assert.assertEquals(path, VariablesStorage.get("PWD"))

    }

    @Test
    fun cdNoFolder() {
        val path = tmpFolder.root.canonicalPath + File.separator + "noSuchFolder"

        val outputStream = ByteArrayOutputStream()
        Assert.assertFalse(Cd(stdin, listOf(path), outputStream.bufferedWriter()).run())
    }

    @Test
    fun cdFile() {
        val file = tmpFolder.newFile("filename")
        val path = file.canonicalPath

        val outputStream = ByteArrayOutputStream()
        Assert.assertFalse(Cd(stdin, listOf(path), outputStream.bufferedWriter()).run())
    }

}
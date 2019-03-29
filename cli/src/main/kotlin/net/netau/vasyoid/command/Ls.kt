package net.netau.vasyoid.command

import net.netau.vasyoid.IncorrectArgumentNumberException
import net.netau.vasyoid.NoSuchFileException
import net.netau.vasyoid.NotADirectoryException
import net.netau.vasyoid.VariablesStorage
import net.netau.vasyoid.util.PathUtil.getBasePath
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.nio.file.FileSystems

/**
 * Ls command. Lists all files in specified directory or in current if unspecified.
 */
class Ls(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {
    override fun run(): Boolean {
        val basePath = getBasePath()
        val path =
            if (arguments.isEmpty())
                basePath
            else {
                basePath.resolve(arguments.first())
            }

        if (arguments.size > 1) {
            throw IncorrectArgumentNumberException("ls", 1, arguments.size)
        }

        val file = path.toFile()


        if (!file.exists()) {
            throw NoSuchFileException("cd", file.canonicalPath)
        }

        if (!file.isDirectory) {
            throw NotADirectoryException("cd", file.canonicalPath)
        }

        stdout.write(listDirectory(file))
        stdout.flush()
        return true
    }

    private fun listDirectory(directory: File): String {
        return directory.listFiles()
            .map { it.relativeTo(directory) }
            .joinToString(System.lineSeparator()) +
                System.lineSeparator()
    }
}
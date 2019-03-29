package net.netau.vasyoid.command

import net.netau.vasyoid.IncorrectArgumentNumberException
import net.netau.vasyoid.NoSuchFileException
import net.netau.vasyoid.NotADirectoryException
import net.netau.vasyoid.VariablesStorage
import net.netau.vasyoid.util.PathUtil.getBasePath
import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Cd command. Moves to specified path or to root if path was not defined.
 */
class Cd(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {
    override fun run(): Boolean {
        if (arguments.isEmpty()) {
            VariablesStorage.set("PWD", System.getProperty("user.dir"))
            return true
        }

        if (arguments.size > 1) {
            throw IncorrectArgumentNumberException("cd", 1, arguments.size)
        }

        val basePath = getBasePath()

        val file = basePath.resolve(arguments.first()).toFile()

        if (!file.exists()) {
            throw NoSuchFileException("cd", file.canonicalPath)
        }

        if (!file.isDirectory) {
            throw NotADirectoryException("cd", file.canonicalPath)
        }

        VariablesStorage.set("PWD", file.canonicalPath)
        return true
    }
}
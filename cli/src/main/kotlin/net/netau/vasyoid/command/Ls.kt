package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File

/**
 * Ls command. Lists all files in specified directory or in current if unspecified.
 */
class Ls(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {
    override fun run(): Boolean {
        val baseDir = VariablesStorage.get("PWD")
        val path =
            if (arguments.isEmpty())
                baseDir
            else {
                val relativePath = arguments.first()
                if (relativePath.startsWith(File.separator))
                    relativePath
                else
                    baseDir + File.separator + relativePath
            }

        val directory = File(path)
        if (!directory.exists() or !directory.isDirectory)
            return false
        stdout.write(listDirectory(directory))
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
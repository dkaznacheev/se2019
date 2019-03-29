package net.netau.vasyoid.command

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

        val basePath = getBasePath()

        val file = basePath.resolve(arguments.first()).toFile()

        if (!file.exists()) {
            println("cd: ${file.canonicalPath} does not exist")
            return false
        }

        if (!file.isDirectory) {
            println("cd: ${file.canonicalPath} is not a directory")
            return false
        }

        VariablesStorage.set("PWD", file.canonicalPath)
        return true
    }
}
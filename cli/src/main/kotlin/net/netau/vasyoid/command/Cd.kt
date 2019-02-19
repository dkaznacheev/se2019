package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File

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

        val basePath = VariablesStorage.get("PWD")

        var path = arguments.first()

        path =
            if (path.startsWith(File.separator))
                path
            else basePath + File.separator + path

        val file = File(path)
        if (!file.exists() or !file.isDirectory) {
            return false
        }

        VariablesStorage.set("PWD", file.canonicalPath)
        return true
    }

}
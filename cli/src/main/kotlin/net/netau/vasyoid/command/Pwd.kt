package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File

/**
 * Pwd command. Prints the current path.
 */
class Pwd(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        stdout.write(VariablesStorage.get("PWD") + System.lineSeparator())
        stdout.flush()
        return true
    }
}

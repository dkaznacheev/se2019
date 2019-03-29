package net.netau.vasyoid.command

import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Echo command. Prints its arguments.
 */
class Echo(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        stdout.write(arguments.joinToString(" "))
        stdout.newLine()
        stdout.flush()
        return true
    }
}

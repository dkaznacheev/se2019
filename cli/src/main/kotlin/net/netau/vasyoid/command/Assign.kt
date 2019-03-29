package net.netau.vasyoid.command

import net.netau.vasyoid.SystemVariableAssignmentException
import net.netau.vasyoid.VariablesStorage
import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Assignment command. Binds an environment variable to a value
 */
class Assign(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        if (arguments.size != 2) {
            System.err.println("Incorrect assignment command")
            return false
        }
        if (arguments[0] == "PWD")
            throw SystemVariableAssignmentException()
        VariablesStorage.set(arguments[0], arguments[1])
        return true
    }
}
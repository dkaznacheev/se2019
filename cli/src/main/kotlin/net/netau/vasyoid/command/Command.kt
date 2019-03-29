package net.netau.vasyoid.command

import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * The common ancestor for all standard commands
 */
abstract class Command(val stdin: BufferedReader, val arguments: List<String>, val stdout: BufferedWriter) {

    /**
     * Main function of the command. Does all business logic.
     * Returns true on success and false otherwise.
     */
    abstract fun run(): Boolean
}

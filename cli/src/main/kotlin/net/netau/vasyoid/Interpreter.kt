package net.netau.vasyoid

import net.netau.vasyoid.command.*
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Interpreter. Interprets parsed user commands.
 */
object Interpreter {

    /**
     * Interprets parsed user commands.
     */
    fun interpret(command: List<String>) {
        var input = ByteArrayInputStream(ByteArray(0)).bufferedReader()
        var mainToken = ""
        val arguments = mutableListOf<String>()
        var output: BufferedWriter

        command.forEach { token ->
            when (token) {
                "|" -> {
                    val outputStream = ByteArrayOutputStream()
                    output = outputStream.bufferedWriter()
                    if (!execute(input, mainToken, arguments as List<String>, output)) {
                        return
                    }
                    input = ByteArrayInputStream(outputStream.toByteArray()).bufferedReader()
                    mainToken = ""
                    arguments.clear()
                }
                "=" -> {
                    if (mainToken == "" || arguments.isNotEmpty()) {
                        System.err.println("Incorrect assignment command")
                        return
                    }
                    arguments += mainToken
                    mainToken = "="
                }
                else -> {
                    if (mainToken == "") {
                        mainToken = token
                    } else {
                        arguments += token
                    }
                }
            }
        }
        execute(input, mainToken, arguments, System.out.bufferedWriter())
    }

    private fun execute(
        input: BufferedReader,
        command: String,
        arguments: List<String>,
        output: BufferedWriter
    ): Boolean {
        if (command == "") {
            System.err.println("Empty command")
            return false
        }
        return when (command) {
            "=" -> Assign(input, arguments, output).run()
            "cat" -> Cat(input, arguments, output).run()
            "echo" -> Echo(input, arguments, output).run()
            "wc" -> Wc(input, arguments, output).run()
            "pwd" -> Pwd(input, arguments, output).run()
            "cd" -> Cd(input, arguments, output).run()
            "ls" -> Ls(input, arguments, output).run()
            "exit" -> {
                System.exit(0)
                true
            }
            else -> ExternalCommand(input, listOf(command) + arguments, output).run()
        }
    }
}

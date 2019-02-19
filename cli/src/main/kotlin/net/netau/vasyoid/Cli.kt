package net.netau.vasyoid

import net.netau.vasyoid.parser.QuotesExpander
import net.netau.vasyoid.parser.VariablesSubstitutor
import net.netau.vasyoid.parser.WordsSplitter

/**
 * Main object. Performs the Cli user interaction.
 */
object Cli {

    private fun processInput(input: String) {
        var tokens = WordsSplitter.split(input)
        tokens = QuotesExpander.expand(tokens)
        tokens = VariablesSubstitutor.substitute(tokens)
        Interpreter.interpret(tokens)
        print("${VariablesStorage.get("PWD")}> ")
    }

    /**
     * Main cli loop.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        print("${VariablesStorage.get("PWD")}> ")
        System.`in`.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                processInput(line)
            }
        }
    }
}
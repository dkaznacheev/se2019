package net.netau.vasyoid.parser

import net.netau.vasyoid.VariablesStorage

/**
 * Variables substitutor. Substitutes variable values to substitutions points.
 */
object VariablesSubstitutor {

    /**
     * Substitutes variable values to substitutions points.
     */
    fun substitute(input: List<String>): List<String> {
        return input.map {
            it.replace(Regex("\u0000\\w+")) { match -> VariablesStorage.get(match.value.drop(1)) }
        }
    }
}

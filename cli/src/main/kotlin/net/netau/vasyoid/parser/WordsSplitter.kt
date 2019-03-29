package net.netau.vasyoid.parser

/**
 * Words splitter. Splits the input into tokens.
 */
object WordsSplitter {

    private val separatorsRegex = Regex("('[^']*'|\"[^\"]*\"|\\||\\s+|=)")
    private const val replacement = "\u0000$1\u0000"

    /**
     * Splits the input into tokens.
     */
    fun split(input: String): List<String> {
        return input.replace(
            separatorsRegex,
            replacement
        )
            .split('\u0000')
            .filter { it.isNotBlank() }
    }
}

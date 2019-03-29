package net.netau.vasyoid.parser

import org.junit.Assert.*
import org.junit.Test

class QuotesExpanderTest {

    @Test
    fun simpleString() {
        val input = listOf("helloWorld", "hello\$World")
        val output = listOf("helloWorld", "hello\u0000World")
        assertEquals(output, QuotesExpander.expand(input))
    }

    @Test
    fun singleQuote() {
        val input = listOf("'hel|o World'", "'hel|o \$World'")
        val output = listOf("hel|o World", "hel|o \$World")
        assertEquals(output, QuotesExpander.expand(input))
    }

    @Test
    fun doubleQuote() {
        val input = listOf("\"hel|o World\"", "\"hel|o \$World\"")
        val output = listOf("hel|o World", "hel|o \u0000World")
        assertEquals(output, QuotesExpander.expand(input))
    }
}

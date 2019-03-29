package net.netau.vasyoid.parser

import org.junit.Test

import org.junit.Assert.*

class WordsSplitterTest {

    @Test
    fun monolithicString() {
        val input = "helloWorld42"
        val output = listOf(input)
        assertEquals(output, WordsSplitter.split(input))
    }

    @Test
    fun stringWithSpaces() {
        val input = "hello World 42"
        val output = listOf("hello", "World", "42")
        assertEquals(output, WordsSplitter.split(input))
    }

    @Test
    fun stringWithPipes() {
        val input = "hello|World | 42"
        val output = listOf("hello", "|", "World", "|", "42")
        assertEquals(output, WordsSplitter.split(input))
    }

    @Test
    fun stringWithAssignment() {
        val input = "hello=World"
        val output = listOf("hello", "=", "World")
        assertEquals(output, WordsSplitter.split(input))
    }

    @Test
    fun stringWithSingleQuotes() {
        val input = "hello'w=o r|l\"d'"
        val output = listOf("hello", "'w=o r|l\"d'")
        assertEquals(output, WordsSplitter.split(input))
    }

    @Test
    fun stringWithSingleQuotesAndSubstitution() {
        val input = "hello'\$world'"
        val output = listOf("hello", "'\$world'")
        assertEquals(output, WordsSplitter.split(input))
    }

    @Test
    fun stringWithDoubleQuotes() {
        val input = "hello\"w=o r|l'd\""
        val output = listOf("hello", "\"w=o r|l'd\"")
        assertEquals(output, WordsSplitter.split(input))
    }

    @Test
    fun stringWithDoubleQuotesAndSubstitution() {
        val input = "hello\"\$world\""
        val output = listOf("hello", "\"\$world\"")
        assertEquals(output, WordsSplitter.split(input))
    }

}
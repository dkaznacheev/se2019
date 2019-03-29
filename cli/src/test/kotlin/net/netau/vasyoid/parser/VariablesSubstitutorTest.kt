package net.netau.vasyoid.parser

import net.netau.vasyoid.VariablesStorage
import org.junit.Assert.*
import org.junit.Test

class VariablesSubstitutorTest {

    @Test
    fun noSubstitution() {
        val input = listOf("hello")
        val output = listOf("hello")
        assertEquals(output, VariablesSubstitutor.substitute(input))
    }

    @Test
    fun existingVariable() {
        VariablesStorage.set("hello", "world")
        val input = listOf("\u0000hello world")
        val output = listOf("world world")
        assertEquals(output, VariablesSubstitutor.substitute(input))
    }

    @Test
    fun variableWithDigits() {
        VariablesStorage.set("he11o", "wor1d")
        val input = listOf("\u0000he11o")
        val output = listOf("wor1d")
        assertEquals(output, VariablesSubstitutor.substitute(input))
    }

    @Test
    fun nonExistingVariable() {
        val input = listOf("hello|\u0000world~")
        val output = listOf("hello|~")
        assertEquals(output, VariablesSubstitutor.substitute(input))
    }
}

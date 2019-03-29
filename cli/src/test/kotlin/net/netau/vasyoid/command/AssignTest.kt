package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import org.junit.Assert.*
import org.junit.Test

class AssignTest {

    private val stdin = System.`in`.bufferedReader()
    private val stdout = System.`out`.bufferedWriter()

    @Test
    fun tooManyArguments() {
        val arguments = listOf("a", "b", "c")
        assertFalse(Assign(stdin, arguments, stdout).run())
    }

    @Test
    fun tooFewArguments() {
        val arguments = listOf("a")
        assertFalse(Assign(stdin, arguments, stdout).run())
    }

    @Test
    fun correctAssignment() {
        val arguments = listOf("a", "asd af")
        assertTrue(Assign(stdin, arguments, stdout).run())
        assertEquals("asd af", VariablesStorage.get("a"))
    }
}
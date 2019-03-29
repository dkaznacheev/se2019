package net.netau.vasyoid

/**
 * Global Variable storage. Stores the values bounded to variables.
 */
object VariablesStorage {

    private val variables = mutableMapOf<String, String>("PWD" to System.getProperty("user.home"))

    /**
     * Gets the value af a variable or ""
     */
    fun get(variable: String): String {
        return variables.getOrDefault(variable, "")
    }

    /**
     * Binds a variable to a value
     */
    fun set(variable: String, value: String) {
        return variables.set(variable, value)
    }
}
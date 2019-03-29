package net.netau.vasyoid

data class NoSuchFileException(val command: String, val file: String): Throwable()
data class NotADirectoryException(val command: String, val file: String): Throwable()
data class IncorrectArgumentNumberException(val command: String, val expected: Int, val actual: Int): Throwable()
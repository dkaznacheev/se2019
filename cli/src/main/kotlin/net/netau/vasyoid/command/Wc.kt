package net.netau.vasyoid.command

import net.netau.vasyoid.util.PathUtil.getBasePath
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.nio.charset.Charset

/**
 * Wc command. Prints the number of newlines, words and bytes in a file.
 */
class Wc(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        if (arguments.isEmpty()) {
            printStat(wc(stdin))
        }
        val total = Statistics()
        val basePath = getBasePath()

        arguments
            .forEach {
                val stat = wc(
                    FileInputStream(basePath.resolve(it).toFile())
                        .reader(Charset.defaultCharset()).buffered())
                printStat(stat, it)
                total.lines += stat.lines
                total.words += stat.words
                total.bytes += stat.bytes
            }
        if (arguments.size > 1) {
            printStat(total, "total")
        }
        return true
    }

    private fun wc(input: BufferedReader): Statistics {
        val stat = Statistics()
        var prevChar = ' '.toInt()
        loop@ while (true) {
            val char = input.read()
            when {
                char < 0 -> break@loop
                char == '\n'.toInt() -> stat.lines++
                !Character.isWhitespace(char) and Character.isWhitespace(prevChar) -> stat.words++
            }
            stat.bytes += charSize(char)
            prevChar = char
        }
        return stat
    }

    private fun charSize(char: Int): Int {
        return char.toChar().toString().toByteArray().size
    }

    private fun printStat(stat: Statistics, name: String = "") {
        stdout.write("${stat.lines} ${stat.words} ${stat.bytes} $name")
        stdout.newLine()
        stdout.flush()
    }

    companion object {

        private data class Statistics(var lines: Int = 0, var words: Int = 0, var bytes: Int = 0)
    }
}

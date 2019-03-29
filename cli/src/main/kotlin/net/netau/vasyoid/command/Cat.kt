package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import java.io.*
import java.nio.file.FileSystems

/**
 * Cat command. Prints the contents of a file.
 */
class Cat(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    /**
     * @inheritDoc
     */
    override fun run(): Boolean {
        if (arguments.isEmpty()) {
            cat(stdin)
        }
        val basePath =  FileSystems.getDefault().getPath(VariablesStorage.get("PWD"))
        return try {
            arguments
                .map { basePath.resolve(it).toUri() }
                .forEach { cat(FileInputStream(File(it)).bufferedReader()) }
            stdout.flush()
            true
        } catch (e: IOException) {
            System.err.println(e.message)
            false
        }
    }

    private fun cat(input: BufferedReader) {
        input.copyTo(stdout)
    }
}

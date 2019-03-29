package net.netau.vasyoid.util

import net.netau.vasyoid.VariablesStorage
import java.nio.file.FileSystems

object PathUtil {
    fun getBasePath() = FileSystems.getDefault().getPath(VariablesStorage.get("PWD"))
}
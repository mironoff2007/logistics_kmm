package ru.mironov.common.data

import okio.FileSystem
import okio.Path.Companion.toPath

actual fun getFilesPath(): String {
    return FileSystem.SYSTEM_TEMPORARY_DIRECTORY.toString()
}

fun makeDir(path: String) {
    FileSystem.SYSTEM.createDirectory(path.toPath(), true)
}
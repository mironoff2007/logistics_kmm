package ru.mironov.common.data

actual fun getFilesPath(): String {
    return System.getProperty("user.dir")
}
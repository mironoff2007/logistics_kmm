package ru.mironov.common

interface Logger {

    fun enable()
    fun disable()

    fun logD(tag: String, msg: String)

    fun logE(tag: String, msg: String)

}

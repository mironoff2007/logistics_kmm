package ru.mironov.common.logging

actual fun consoleLog(tag:String,text: String) {
    println("$tag:$text")
}
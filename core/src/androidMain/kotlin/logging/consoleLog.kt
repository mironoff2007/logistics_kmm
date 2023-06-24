package ru.mironov.common.logging

import android.util.Log

actual fun consoleLog(tag:String, text: String) {
    Log.d(tag, text)
}
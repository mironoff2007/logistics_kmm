package ru.mironov.logistics.logging

import data.file.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.data.DataConstants
import ru.mironov.common.data.getFilesPath
import ru.mironov.common.logging.consoleLog
import ru.mironov.common.util.DateTimeFormat
import ru.mironov.domain.di.Singleton

@Singleton
class LoggerImpl @Inject constructor(): Logger {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var file: File? = null

    init {
        scope.launch {
            file = createFile()
        }
    }

    private var enabled: Boolean = true

    override fun enable() {
        enabled = true
    }

    override fun disable() {
        enabled = false
    }

    private fun getPath() = "${getFilesPath()}/${DataConstants.APP_FOLDER_NAME}/${DataConstants.LOGS_FOLDER_NAME}/"

    private fun createFile(): File {
        var name = DateTimeFormat.formatLog(Clock.System.now().epochSeconds)
        val file = File()

        try {
            while (name.contains(" ")) name = name.replace(" ","_")
            while (name.contains(":")) name = name.replace(":","-")
            file.create(getPath(),"$name.log" )
        }
        catch (e: Exception) {
            e.stackTraceToString()
        }

        return file
    }

    override fun logD(tag: String, msg: String) {
        scope.launch {
            if (enabled) {
                val line = "[${getTime()}]-$msg"
                val logTag = "$DEBUG_TAG:$tag"
                consoleLog(logTag, line)
                save(line)
            }
        }
    }

    override fun logE(tag: String, msg: String) {
        scope.launch {
            if (enabled) {
                val line = "[${getTime()}]-$msg"
                val logTag = "$ERROR_TAG:$tag:"
                consoleLog(logTag, line)
                save(line)
            }
        }
    }

    private fun getTime(): String {
        val date = Clock.System.now().epochSeconds
        return DateTimeFormat.formatLog(date) ?: date.toString()
    }

    private suspend fun save(text: String): Boolean {
        return file?.write(text)?.isSuccess == true
    }

    companion object {
        const val DEBUG_TAG = "DEBUG"
        const val ERROR_TAG = "ERROR"
    }
}

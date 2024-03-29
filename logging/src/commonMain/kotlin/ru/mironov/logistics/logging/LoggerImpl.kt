package ru.mironov.logistics.logging

import data.file.MultiplatformFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.common.data.DataConstants
import ru.mironov.common.data.getFilesPath
import ru.mironov.common.logging.consoleLog
import ru.mironov.domain.di.AppScope
import util.DateTimeFormat

@AppScope
class LoggerImpl @Inject constructor(): Logger {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var file: MultiplatformFile? = null
    private var createJob: Job? = null
    private var logFiles : List<String> = emptyList()

    init {
        createJob = scope.launch {
            file = createFile()
            logFiles =  file?.listPath()?.getOrDefault(emptyList()) ?: emptyList()
            cleanOldLogs()
        }
    }

    private fun cleanOldLogs() {
        val files = logFiles.map { path ->
            var index = path.indexOfLast { char -> char == "\\".single() }
            if (index == -1) index = path.indexOfLast { char -> char == "/".single() }
            val filePath = path.subSequence(0, index).toString()
            val fileName = path.subSequence(index + 1, path.length).toString()
            MultiplatformFile(filePath, fileName)
        }
        val sorted = files.sortedByDescending { DateTimeFormat.toDate(it.name(), DateTimeFormat.LOG_FILE_FORMAT) }
        sorted.forEachIndexed { index, multiplatformFile ->
            if (index + 1 > KEEP_COUNT) {
                val res = multiplatformFile.delete()
                logD(LOG_TAG, "delete ${multiplatformFile.name()} $res")
            }
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

    private fun createFile(): MultiplatformFile? {
        var name = DateTimeFormat.formatLogFile(Clock.System.now().epochSeconds * 1000)
        return try {
            while (name.contains(" ")) name = name.replace(" ","_")
            while (name.contains(":")) name = name.replace(":","-")
            MultiplatformFile(getPath(),"$name.log" )
        }
        catch (e: Exception) {
            e.stackTraceToString()
            null
        }
    }

    override fun logD(tag: String, msg: String) {
        scope.launch {
            if (enabled) {
                val line = "[${getTime()}]-$msg"
                val logTag = "$DEBUG_TAG:$tag"
                consoleLog(logTag, line)
                save(fileLogLine(logTag, msg))
            }
        }
    }

    override fun logE(tag: String, msg: String) {
        scope.launch {
            if (enabled) {
                val line = "[${getTime()}]-$msg"
                val logTag = "$ERROR_TAG:$tag"
                consoleLog(logTag, line)
                save(fileLogLine(logTag, msg))
            }
        }
    }

    private fun fileLogLine(tag: String, msg: String) = "[${getTime()}]:$tag-$msg"

    private fun getTime(): String {
        val date = Clock.System.now().toEpochMilliseconds()
        return DateTimeFormat.formatLog(date)
    }

    private suspend fun save(text: String): Boolean {
        createJob?.join()
        return file?.append(text)?.isSuccess == true
    }

    companion object {
        const val DEBUG_TAG = "DEBUG"
        const val ERROR_TAG = "ERROR"
        const val LOG_TAG = "LOGGER"
        private const val KEEP_COUNT = 3
    }
}

package ru.mironov.common.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


object DateTimeFormat {

    fun format(time: Long, format: String): String? {
       return try {
            val date = Instant.fromEpochMilliseconds(time)
            val dateFormat = date.toLocalDateTime(TimeZone.currentSystemDefault())
            dateFormat.toString() // todo
        }
        catch (e:Exception) {
            null
        }
    }

    fun formatUI(time: Long) = format(time, UI_FORMAT)
    fun formatDB(time: Long) = format(time, DB_FORMAT)
    fun formatLog(time: Long) = format(time, LOG_FORMAT)

    private const val UI_FORMAT = "dd.MM.yyyy HH:mm"
    private const val DB_FORMAT = "dd.MM.yyyy_HH:mm:ss"
    private const val LOG_FORMAT = "dd.MM.yyyy-HH:mm:ss.SSS"
}
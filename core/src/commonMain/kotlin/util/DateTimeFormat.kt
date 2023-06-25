package ru.mironov.common.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


object DateTimeFormat {

    fun format(timeInMillis: Long, format: String): String {
        val date = Instant.fromEpochMilliseconds(timeInMillis)
        val dateFormat = date.toLocalDateTime(TimeZone.currentSystemDefault())
        var result = format
        return try {
            var millis = timeInMillis.toString()
            millis = millis.substring(millis.length - 3, millis.length)
            result = result.replace("yyyy", dateFormat.year.toString())
            if (result.contains("dd")) result = result.replace("dd", dateFormat.dayOfMonth.formatWithZero())
            if (result.contains("MM")) result = result.replace("MM", dateFormat.monthNumber.formatWithZero())
            if (result.contains("HH")) result = result.replace("HH", dateFormat.second.formatWithZero())
            if (result.contains("mm")) result = result.replace("mm", dateFormat.minute.formatWithZero())
            if (result.contains("ss")) result = result.replace("ss", dateFormat.second.formatWithZero())
            if (result.contains("SSS")) result = result.replace("SSS", millis )
            result
        } catch (e: Exception) {
            dateFormat.toString()
        }
    }

    private fun Int.formatWithZero(): String {
        val str = this.toString()
        return if (str.length  == 1) "0$str" else str
    }

    fun formatUI(time: Long) = format(time, UI_FORMAT)
    fun formatDB(time: Long) = format(time, DB_FORMAT)
    fun formatLog(time: Long) = format(time, LOG_FORMAT)

    private const val UI_FORMAT = "dd.MM.yyyy HH:mm"
    private const val DB_FORMAT = "dd.MM.yyyy_HH:mm:ss"
    private const val LOG_FORMAT = "dd.MM.yyyy-HH:mm:ss.SSS"
}
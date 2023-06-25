package util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


object DateTimeFormat {

    private fun format(timeInMillis: Long, format: String): String {
        val date = Instant.fromEpochMilliseconds(timeInMillis)
        val dateFormat = date.toLocalDateTime(TimeZone.currentSystemDefault())
        var result = format
        return try {
            var millis = timeInMillis.toString()
            millis = millis.substring(millis.length - 3, millis.length)
            result = result.replace("yyyy", dateFormat.year.toString())
            result = result.replaceIfContains("dd", dateFormat.dayOfMonth.formatWithZero())
            result = result.replaceIfContains("MM", dateFormat.monthNumber.formatWithZero())
            result = result.replaceIfContains("HH", dateFormat.hour.formatWithZero())
            result = result.replaceIfContains("mm", dateFormat.minute.formatWithZero())
            result = result.replaceIfContains("ss", dateFormat.second.formatWithZero())
            result = result.replaceIfContains("SSS", millis )
            result
        } catch (e: Exception) {
            dateFormat.toString()
        }
    }

    private fun Int.formatWithZero(): String {
        val str = this.toString()
        return if (str.length  == 1) "0$str" else str
    }

    private fun String.replaceIfContains(tag: String, value: String): String = if (this.contains(tag)) replace(tag, value ) else this


    fun formatUI(time: Long) = format(time, UI_FORMAT)
    fun formatDB(time: Long) = format(time, DB_FORMAT)
    fun formatLog(time: Long) = format(time, LOG_FORMAT)

    private const val UI_FORMAT = "dd.MM.yyyy HH:mm"
    private const val DB_FORMAT = "dd.MM.yyyy_HH:mm:ss"
    private const val LOG_FORMAT = "dd.MM.yyyy-HH:mm:ss.SSS"

}
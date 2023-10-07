package util

import kotlinx.datetime.DateTimeUnit
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

    fun toDate(str: String, format: String): Instant? =
        try {
            val month = str.valueUnderTag(tag = MONTH, format = format) ?: "00"
            val day = str.valueUnderTag(tag = DAY, format = format) ?: "00"
            val year = str.valueUnderTag(tag = YEAR, format = format) ?: "0000"
            val hour = str.valueUnderTag(tag = HOUR, format = format) ?: "00"
            val minute = str.valueUnderTag(tag = MINUTE, format = format) ?: "00"
            val seconds = str.valueUnderTag(tag = SECONDS, format = format) ?: "00"

            val iso = "$year-$month-${day}T$hour:$minute:${seconds}Z"
            val instant = Instant.parse(iso)
            instant
        } catch (e: Exception) {
            null
        }

    private fun String.valueUnderTag(tag: String, format: String): String? =
        try {
            this.substring(format.indexOf(tag), format.indexOf(tag) + tag.length)
        } catch (e: Exception) {
            null
        }

    private fun Int.formatWithZero(): String {
        val str = this.toString()
        return if (str.length  == 1) "0$str" else str
    }

    private fun String.replaceIfContains(tag: String, value: String): String = if (this.contains(tag)) replace(tag, value ) else this


    fun formatUI(time: Long) = format(time, UI_FORMAT)
    fun formatDB(time: Long) = format(time, DB_FORMAT)
    fun formatLog(time: Long) = format(time, LOG_FORMAT)

    fun formatLogFile(time: Long) = format(time, LOG_FILE_FORMAT)

    private const val UI_FORMAT = "dd.MM.yyyy HH:mm"
    private const val DB_FORMAT = "dd.MM.yyyy_HH:mm:ss"
    const val LOG_FILE_FORMAT = "dd-MM-yyyy_HH:mm:ss"
    private const val LOG_FORMAT = "dd.MM.yyyy-HH:mm:ss.SSS"
    private const val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    private const val MONTH = "MM"
    private const val DAY = "dd"
    private const val YEAR = "yyyy"
    private const val HOUR = "HH"
    private const val MINUTE = "mm"
    private const val SECONDS = "ss"

}
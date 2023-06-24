package ru.mironov.common.data

import Database
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

actual class DriverFactory actual constructor() {
    actual fun createDriver(name: String): SqlDriver {
        val path = getFilesPath()

        val filePath = "$path/$name.db"
        return JdbcSqliteDriver("jdbc:sqlite:$filePath").apply {
            if (!File(filePath).exists()) {
                Database.Schema.create(this)
            }
        }
    }
}
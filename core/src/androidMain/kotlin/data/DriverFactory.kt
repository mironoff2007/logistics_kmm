package ru.mironov.common.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ru.mironov.common.AppContext

actual class DriverFactory actual constructor() {
    actual fun createDriver(name: String): SqlDriver {
        val context = AppContext.appContext
        return AndroidSqliteDriver(Database.Schema, context, name)
    }
}
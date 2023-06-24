package ru.mironov.common.data

import Database
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DriverFactory actual constructor() {
    actual fun createDriver(name: String): SqlDriver = NativeSqliteDriver(Database.Schema, name)
}
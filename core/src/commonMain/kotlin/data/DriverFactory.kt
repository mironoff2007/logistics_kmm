package ru.mironov.common.data

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory() {
    fun createDriver(name: String): SqlDriver

}
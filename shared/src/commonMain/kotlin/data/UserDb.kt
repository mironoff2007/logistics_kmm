package ru.mironov.common.data

import app.cash.sqldelight.Query
import app.cash.sqldelight.db.SqlDriver
import data.UserEntity
import data.UserQueries

class UserDb(private val driver: SqlDriver) {

    private val userQueries: UserQueries = UserQueries(driver)

    fun drop() {
        userQueries.drop()
    }

    fun getAllUsers(): Query<UserEntity> {
        return userQueries.selectAll()
    }

}
package com.mironov.localization.errors

object RussianErrors {
    fun getString(stringRes: ErrorLocalized): String {
        return when (stringRes) {
            ErrorLocalized.WrongPasswordOrUser -> "Неправильный логин или пароль"
            else -> ""
        }
    }
}
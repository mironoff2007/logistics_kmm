package com.mironov.localization.errors

object EnglishErrors {
    fun getString(stringRes: ErrorLocalized): String {
        return when (stringRes) {
            ErrorLocalized.WrongPasswordOrUser -> "Incorrect username or password"
            else -> ""
        }
    }
}
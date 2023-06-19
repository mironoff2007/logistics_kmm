package ru.mironov.domain.model.web

import com.mironov.localization.Localization
import com.mironov.localization.errors.ErrorLocalized

object ErrorCodes{
    private fun codeToError(code: Int) = when (code) {
        1 -> ErrorLocalized.WrongPasswordOrUser
        else -> ErrorLocalized.None
    }

    fun localizedError(code: Int) =  Localization.getError(codeToError(code))
}

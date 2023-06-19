package com.mironov.localization

import com.mironov.localization.errors.EnglishErrors
import com.mironov.localization.errors.ErrorLocalized
import com.mironov.localization.errors.RussianErrors

object Localization {

    private var currentLang = Language.English

    fun setLocale(locale: String) {
        val lang = when (locale) {
            Language.English.key -> Language.English
            Language.Russian.key -> Language.Russian
            else -> Language.English
        }
        currentLang = lang
    }

    fun getByLocale(locale: String): Language {
        return when (locale) {
            Language.English.key -> Language.English
            Language.Russian.key -> Language.Russian
            else -> Language.English
        }
    }

    fun setLang(lang: Language) {
        currentLang = lang
    }

    enum class Language(val key: String, val showName: String) {
        English("en", "English"),
        Russian("ru", "Рyсский");

        companion object {
            fun getByShowName(name: String): Language {
                return when (name) {
                    English.showName -> English
                    Russian.showName -> Russian
                    else -> English
                }
            }
        }
    }

    fun getString(stringRes: StringRes): String {
        return when (currentLang) {
            Language.English -> English.getString(stringRes)
            Language.Russian -> Russian.getString(stringRes)
        }
    }

    fun getError(stringRes: ErrorLocalized): String {
        return when (currentLang) {
            Language.English -> EnglishErrors.getString(stringRes)
            Language.Russian -> RussianErrors.getString(stringRes)
        }
    }
}


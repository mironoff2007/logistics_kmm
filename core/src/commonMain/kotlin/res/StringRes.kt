package ru.mironov.common.res

import com.mironov.localization.Localization
import com.mironov.localization.StringRes
import com.mironov.localization.errors.ErrorLocalized

fun localizedString(stringRes: StringRes): String {
    return Localization.getString(stringRes)
}


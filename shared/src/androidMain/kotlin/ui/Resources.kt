package ru.mironov.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ru.mironov.common.res.ImageRes

@Composable
actual fun getLocale(): String {
    val context = LocalContext.current
    return context.resources.configuration.locales.get(0).language
}
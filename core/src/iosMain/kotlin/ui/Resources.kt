package ru.mironov.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ru.mironov.common.res.ImageRes

@Composable
actual fun getLocale(): String {
    return "en"
}
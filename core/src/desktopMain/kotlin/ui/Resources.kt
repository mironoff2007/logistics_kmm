package ru.mironov.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import ru.mironov.common.res.ImageRes
import java.util.Locale

@Composable
actual fun getLocale(): String {
    return Locale.getDefault().language
}
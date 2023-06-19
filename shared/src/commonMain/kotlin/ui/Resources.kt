package ru.mironov.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import ru.mironov.common.res.ImageRes

@Composable
expect fun getPainterResource(imageRes: ImageRes): Painter

@Composable
expect fun getLocale(): String
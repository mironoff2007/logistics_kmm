package ru.mironov.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.mironov.logistics.ui.theme.LightBlack
import ru.mironov.logistics.ui.theme.Main
import ru.mironov.logistics.ui.theme.MainDark
import ru.mironov.logistics.ui.theme.Purple700
import ru.mironov.logistics.ui.theme.Shapes
import ru.mironov.logistics.ui.theme.Typography

private val DarkColorPalette = darkColors(
    primary = MainDark,
    primaryVariant = Purple700,
    secondary = Color.Blue,
    surface = LightBlack,
    background = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.LightGray,
    onSurface = Color.LightGray,
    onBackground = Color.LightGray
)

private val LightColorPalette = lightColors(
    primary = Main,
    primaryVariant = Main,
    secondary = Main,
    surface = Color.White,
    background = Color.LightGray,
    onPrimary = Color.White,
    onBackground = Color.Black
)

@Composable
fun LogisticsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
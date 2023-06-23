package ru.mironov.logistics.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import ru.mironov.logistics.ui.theme.LogisticsTheme

@Composable
fun LoginPreview() {
    LogisticsTheme {
        LoginLayout({ _, _ -> }, false, TextFieldValue("")) {}
    }
}
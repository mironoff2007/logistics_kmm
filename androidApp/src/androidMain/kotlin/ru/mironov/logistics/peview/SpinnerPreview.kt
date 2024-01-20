package ru.mironov.logistics.peview


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.toImmutableList
import ru.mironov.common.ui.Spinner
import ru.mironov.common.ui.theme.LogisticsTheme

@Preview()
@Composable
fun SpinnerPreview() {
    LogisticsTheme {
        val list = listOf("1", "2").toImmutableList()
        Spinner(list = list, onValueChange = {} , selected = "numbs", label = "number")
    }
}

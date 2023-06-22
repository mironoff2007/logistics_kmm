package com.logistics.peview


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.toPersistentList
import ru.mironov.common.ui.Spinner
import ru.mironov.logistics.ui.theme.LogisticsTheme

@Preview()
@Composable
fun SpinnerPreview() {
    LogisticsTheme {
        val list = listOf("1", "2").toPersistentList()
        Spinner(list = list, onValueChange = {} , selected = "numbs", label = "number")
    }
}

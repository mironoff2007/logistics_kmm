package com.mironov

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.toPersistentList
import ru.mironov.common.ui.Spinner
import ru.mironov.common.ui.theme.LogisticsTheme

@Preview
@Composable
fun AppPreview() {
    LogisticsTheme {
        val list = listOf("1", "2").toPersistentList()
        Spinner(list = list, onValueChange = {} , selected = "numbs", label = "number")
    }
}
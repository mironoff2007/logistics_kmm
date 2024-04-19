package ru.mironov.logistics.ui.screens.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun DialogScreen(dialogData: DialogAction.DialogData?, dialogAction: DialogAction) {
    dialogData?.msg?.let { DialogLayout(dialogData.msg) }
    LaunchedEffect(dialogData) {
        delay(dialogData?.duration ?: 0)
        dialogAction.hide()
    }
}
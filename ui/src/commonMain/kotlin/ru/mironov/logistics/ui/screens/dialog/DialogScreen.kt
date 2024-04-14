package ru.mironov.logistics.ui.screens.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.delay

@Composable
fun DialogScreen(dialogData: DialogAction.DialogData?, dialogAction: MutableState<DialogAction>) {
    dialogData?.msg?.let { DialogLayout(dialogData.msg) }
    LaunchedEffect(dialogData) {
        delay(dialogData?.duration ?: 0)
        dialogAction.value.hide()
    }
}
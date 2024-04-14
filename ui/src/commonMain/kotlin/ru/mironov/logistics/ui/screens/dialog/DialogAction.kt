package ru.mironov.logistics.ui.screens.dialog

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DialogAction {

    class DialogData(val msg: String, val duration: Long = 3000)

    private val _show = MutableStateFlow<DialogData?>(null)
    val showFlow: StateFlow<DialogData?>
        get() { return _show.asStateFlow() }

    fun show(dialogData: DialogData) {
        _show.tryEmit(dialogData)
    }

    fun hide() {
        _show.tryEmit(null)
    }
}
package ru.mironov.logistics.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SingleEventFlow<T> {

    val flow: StateFlow<T?>
        get() { return _flow.asStateFlow() }

    private val  _flow = MutableStateFlow<T?>(null)

    var state: T? = null

    /**
     *Call observe before calling onEvent
     */
    fun onEvent(onEvent: (T) -> Unit) {
        val currentState = state
        _flow.value = null
        currentState?.let { onEvent.invoke(currentState) }
    }

    suspend fun postEventSuspend(event: T) {
        _flow.emit(event)
    }

    fun postEvent(event: T) {
        _flow.tryEmit(event)
    }

    @Composable
    inline fun Observe() {
        val currentState by flow.collectAsState()
        this.state = currentState
    }

}

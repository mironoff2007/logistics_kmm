package ru.mironov.domain.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel

abstract class ViewModel(): AbsViewModel() {

    protected val viewModelScope = CoroutineScope(Dispatchers.IO)
    override fun onDestroy() {
        viewModelScope.cancel()
    }
}
package ru.mironov.logistics.ui.screens.registerresult

import com.mironov.database.parcel.ParcelsDbSource
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.domain.model.Parcel
import ru.mironov.domain.viewmodel.ViewModel
import ru.mironov.logistics.ui.SingleEventFlow

@Inject
class RegisterResultViewModel(
    private val parcelsDbSource: ParcelsDbSource,
    val logger: Logger
): ViewModel() {

    private val supervisor = SupervisorJob()

    val result = SingleEventFlow<Boolean>()

    private val _loading = MutableStateFlow(false)

    val loading: StateFlow<Boolean>
        get() { return _loading.asStateFlow() }

    private val _parcel = MutableStateFlow<Parcel?>(null)
    val parcel: StateFlow<Parcel?>
        get() { return _parcel.asStateFlow() }

    fun onScreenOpened(arg: String) {
        viewModelScope.launch(supervisor) {
            try {
                _loading.emit(true)
                val parcel = Json.decodeFromString<Parcel>(arg)
                _parcel.emit(parcel)
            } catch (e: Exception) {
                logger.logE(LOG_TAG, e.stackTraceToString())
            } finally {
                _loading.emit(false)
            }
        }
    }

    fun submit() {
        viewModelScope.launch(supervisor) {
            try {
                _loading.emit(true)
                parcelsDbSource.replace(_parcel.value!!)
                result.postEventSuspend(true)
            } catch (e: Exception) {
                logger.logE(LOG_TAG, e.stackTraceToString())
            } finally {
                _loading.emit(false)
            }
        }
    }

    companion object {
        private const val LOG_TAG = "RegisterResultVM"
    }

}
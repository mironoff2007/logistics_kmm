package ru.mironov.logistics.ui.screens.parceldata

import com.mironov.database.parcel.ParcelsDbSource
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
class ParcelDataViewModel(
    private val parcelsDbSource: ParcelsDbSource,
    val logger: Logger
) : ViewModel() {

    val result = SingleEventFlow<Boolean>()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() { return _loading.asStateFlow() }

    private val _parcel = MutableStateFlow<Parcel?>(null)
    val parcel: StateFlow<Parcel?>
        get() { return _parcel.asStateFlow() }

    fun onScreenOpened(args: String?) {
        viewModelScope.launch {
            try {
                logger.logD(LOG_TAG, "Parcel data screen is opened")
                getParcel(args!!)
            }
            catch (e: Exception) {
                logger.logE(LOG_TAG, e.stackTraceToString())
            }
        }
    }

    suspend fun getParcel(args: String) {
        val parcelData = Json.decodeFromString<ParcelDataArg>(args)
        val parcelFromDb = parcelsDbSource.get(parcelData.id)
        _parcel.emit(parcelFromDb)
    }

    companion object {
        private const val LOG_TAG = "ParcelDataVM"
    }

}
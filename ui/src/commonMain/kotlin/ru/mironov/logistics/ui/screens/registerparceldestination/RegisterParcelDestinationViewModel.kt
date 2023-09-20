package ru.mironov.logistics.ui.screens.registerparceldestination

import com.mironov.database.city.CityDbSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import util.DateTimeFormat
import ru.mironov.domain.model.City
import ru.mironov.domain.model.Parcel
import ru.mironov.domain.model.ParcelData
import ru.mironov.domain.viewmodel.ViewModel
import ru.mironov.logistics.ui.SingleEventFlow
@Inject
class RegisterParcelDestinationViewModel(
    private val cityDbSource: CityDbSource,
    val logger: Logger
): ViewModel() {

    private var parcelSenderData: ParcelData? = null
    var parcelRecipientData: ParcelData? = null

    val navWithArg = SingleEventFlow<String>()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() { return _loading.asStateFlow() }

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>>
        get() { return _cities.asStateFlow() }

    fun onScreenOpened(args: String?) {
        viewModelScope.launch {
            try {
                args?.let { parseParcelSenderData(args) }
                getCities()
            }
            catch (e: Exception) {
                logger.logE(LOG_TAG, e.stackTraceToString())
            }
        }
    }

    private suspend fun getCities() {
        _loading.emit(true)
        try {
            val cities = cityDbSource.fetchAll()
            logger.logD(LOG_TAG, "load cities: $cities")
            _cities.emit(cities)
        } catch (e: Exception) {
            logger.logE(LOG_TAG, e.stackTraceToString())
        } finally {
            _loading.emit(false)
        }
    }

    fun addParcel(recipientData: ParcelData) {
        viewModelScope.launch {
            _loading.emit(true)
            try {
                val time = Clock.System.now().toEpochMilliseconds()
                parcelRecipientData = ParcelData(
                    personName = recipientData.personName,
                    personSecondName = recipientData.personSecondName,
                    address = recipientData.address,
                    city = recipientData.city
                )
                val parcel = Parcel(
                    parcelId = time,
                    customerName = recipientData.personName,
                    customerSecondName = recipientData.personSecondName,
                    address = recipientData.address,
                    senderName = parcelSenderData!!.personName,
                    senderSecondName = parcelSenderData!!.personSecondName,
                    senderAddress = parcelSenderData!!.address,
                    dateShow = DateTimeFormat.formatDB(time) ?: time.toString(),
                    destinationCity = recipientData.city!!,
                    currentCity = parcelSenderData!!.city!!,
                    senderCity = parcelSenderData!!.city!!,
                )
                val argString = Json.encodeToString(parcel)
                navWithArg.postEventSuspend(argString)
            } catch (e: Exception) {
                logger.logE(LOG_TAG, e.stackTraceToString())
            }
            finally {
                _loading.emit(false)
            }
        }
    }

    private suspend fun parseParcelSenderData(args: String) {
        val parcelData = Json.decodeFromString<ParcelSenderDataArg>(args)
        parcelSenderData = parcelData.data
    }

    companion object {
        private const val LOG_TAG = "RegisterDestinationParcelVM"
    }

}
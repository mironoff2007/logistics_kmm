package ru.mironov.logistics.ui.screens.registerparcelsender

import com.mironov.database.city.CityTable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.data.DataBase.initCityTable
import ru.mironov.common.data.DataBase.initParcelsTable
import ru.mironov.domain.model.City
import ru.mironov.domain.model.ParcelData
import ru.mironov.common.Logger
import ru.mironov.logistics.ui.SingleEventFlow
import ru.mironov.logistics.ui.screens.registerparceldestination.ParcelSenderDataArg
import ru.mironov.domain.viewmodel.ViewModel

@Inject
class RegisterParcelSenderViewModel(
    private val cityTable: CityTable,
    val logger: Logger
) : ViewModel() {

    val navWithArg = SingleEventFlow<String>()

    var senderName = ""
    var senderSecondName = ""
    var senderAddress = ""
    var senderCity: City? = null

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() { return _loading.asStateFlow() }

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>>
        get() { return _cities.asStateFlow() }

    fun onScreenOpened() {
        viewModelScope.launch {
            initCityTable()
            getCities()
        }
    }

    private suspend fun getCities() {
        initCityTable()
        _loading.emit(true)
        try {
            val cities = cityTable.fetchAll()
            logger.logD(LOG_TAG, "load cities: $cities")
            _cities.emit(cities)
        } catch (e: Exception) {
            logger.logE(LOG_TAG, e.stackTraceToString())
        } finally {
            _loading.emit(false)
        }
    }


    fun addParcel(senderParcelData: ParcelData) {
        viewModelScope.launch {
            _loading.emit(true)
            try {
                initParcelsTable()
                val parcel = ParcelData(
                    personName = senderParcelData.personName,
                    personSecondName = senderParcelData.personSecondName,
                    address = senderParcelData.address,
                    city = senderParcelData.city!!,
                )
                logger.logD(LOG_TAG, "add parcel sender data: $parcel")
                val arg = ParcelSenderDataArg(parcel)
                val argString = Json.encodeToString(arg)
                navWithArg.postEventSuspend(argString)
            } catch (e: Exception) {
                logger.logE(LOG_TAG, e.stackTraceToString())
            }
            finally {
                _loading.emit(false)
            }
        }
    }

    companion object {
        private const val LOG_TAG = "RegisterParcelVM"
    }

}
package ru.mironov.logistics.ui.screens.store

import com.mironov.database.city.CityDbSource
import com.mironov.database.parcel.ParcelsDbSource
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject

import ru.mironov.domain.model.City
import ru.mironov.domain.model.Parcel
import ru.mironov.common.Logger
import ru.mironov.logistics.ui.SingleEventFlow
import ru.mironov.domain.viewmodel.ViewModel
import ru.mironov.logistics.repo.ParcelsRepo
import ru.mironov.logistics.repo.UserSessionRepo
import ru.mironov.logistics.ui.navigation.Screens
import ru.mironov.logistics.ui.screens.parceldata.ParcelDataArg

@Inject
class StoreViewModel(
    private val parcelsDbSource: ParcelsDbSource,
    private val sessionRepo: UserSessionRepo,
    private val parcelsRepo: ParcelsRepo,
    private val cityDbSource: CityDbSource,
    val logger: Logger
) : ViewModel() {

    val navWithArg = SingleEventFlow<String?>()

    private val supervisor = SupervisorJob()

    private val _loading = MutableStateFlow(false)

    private  var searchJob: Job? = null
    val loading: StateFlow<Boolean>
        get() { return _loading.asStateFlow() }

    private val _parcels = MutableStateFlow<List<Parcel>>(emptyList())
    val parcels: StateFlow<List<Parcel>>
        get() { return _parcels.asStateFlow() }

    private val _cities = MutableStateFlow<List<City?>>(emptyList())
    val cities: StateFlow<List<City?>>
        get() { return _cities.asStateFlow() }

    fun onScreenOpened(
        search: String,
        currentCity: City?,
        destinationCity: City?,
        screen: Screens
    ) {
        viewModelScope.launch(supervisor) {
            try {
                _loading.emit(true)
                val cities = cityDbSource.fetchAll()
                val citiesWithNull = mutableListOf<City?>()
                citiesWithNull.add(null)
                citiesWithNull.addAll(cities)

                _cities.emit(citiesWithNull)
                search(
                    search = search,
                    currentCity = currentCity,
                    destinationCity = destinationCity,
                    screen = screen
                )
            } catch (e: Exception) {
                logger.logE(LOG_TAG, e.stackTraceToString())
            } finally {
                _loading.emit(false)
            }
        }
    }

    fun postArgsAndGo(parcel: Parcel) {
        viewModelScope.launch {
            _loading.emit(true)
            try {
                val json = Json.encodeToString(ParcelDataArg(parcel))
                navWithArg.postEvent(json)
            } catch (e: Exception) {
                logger.logE(LOG_TAG, e.stackTraceToString())
            } finally {
                _loading.emit(false)
            }
        }
    }

    fun search(
        search: String,
        currentCity: City?,
        destinationCity: City?,
        screen: Screens
    ) {
        searchJob?.let {
            try {
                it.cancel()
            } catch (e: Exception) { }
        }
        searchJob = viewModelScope.launch {
            _loading.emit(true)
            try {
                val storeId =
                    if (screen != Screens.GlobalSearch) sessionRepo.getUserData()?.userStoreId
                    else null
                val parcels = parcelsRepo.searchParcels(
                    searchBy = search,
                    fromCityId = currentCity?.id.toString(),
                    toCityId = destinationCity?.id.toString(),
                    storeId = storeId
                )
                _parcels.emit(parcels)
            } catch (e: Exception) {
                logger.logE(LOG_TAG, e.stackTraceToString())
            } finally {
                _loading.emit(false)
            }
        }
    }

    companion object {
        private const val LOG_TAG = "WarehouseVM"
    }
}
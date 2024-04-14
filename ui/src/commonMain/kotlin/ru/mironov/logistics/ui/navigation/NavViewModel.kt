package ru.mironov.logistics.ui.navigation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import ru.mironov.common.Logger
import ru.mironov.domain.settings.CommonSettings
import ru.mironov.domain.viewmodel.ViewModel
import ru.mironov.logistics.SharedPreferences
import ru.mironov.logistics.UserRole
import ru.mironov.logistics.repo.UserSessionRepo
import ru.mironov.logistics.ui.navigation.navcontroller.NavController

@Inject
class NavViewModel(
    private val sessionRepo: UserSessionRepo,
) : ViewModel() {

    private val _allowedDestinations = MutableStateFlow<List<Screens>>(emptyList())
    val allowedDestinations: StateFlow<List<Screens>>
        get() {
            return _allowedDestinations.asStateFlow()
        }

    private val _showMsg = MutableStateFlow<String?>(null)
    val showMsg: StateFlow<String?>
        get() {
            return _showMsg.asStateFlow()
        }

    fun showMsg(msg: String, delay: Long = 3000) {
        viewModelScope.launch {
            _showMsg.emit(msg)
            delay(delay)
            _showMsg.emit(null)
        }
    }

    fun getAllowedDestinationsFor(screen: Screens) {
        viewModelScope.launch {
            val userRole = sessionRepo.getRole()
            val list = when (screen) {
                Screens.LoginScreen,
                Screens.LogoutScreen -> listOf(Screens.SettingsScreenLoggedOut)

                Screens.RegisterSenderParcel,
                Screens.CarCargo,
                Screens.Warehouse,
                Screens.SettingsScreen,
                Screens.GlobalSearch,
                Screens.BackPack -> {
                    mutableListOf(
                        Screens.LogoutScreen,
                        Screens.SettingsScreen
                    ).apply { addAll(byRole(userRole)) }
                }

                Screens.RegisterDestinationParcel,
                Screens.RegisterResult -> listOf(Screens.Back)

                Screens.SettingsScreenLoggedOut -> listOf(Screens.LogoutScreen)
                Screens.ParcelData -> listOf(Screens.Back)

                else -> listOf()
            }
            _allowedDestinations.emit(list)
        }
    }

    private fun byRole(userRole: UserRole?): List<Screens> =
        when (userRole) {
            UserRole.COURIER -> listOf(
                Screens.RegisterSenderParcel,
                Screens.BackPack,
                Screens.GlobalSearch
            )

            UserRole.DRIVER -> listOf(
                Screens.CarCargo,
                Screens.GlobalSearch
            )

            UserRole.SERVICE_POINT_MANAGER -> listOf(
                Screens.RegisterSenderParcel,
                Screens.Warehouse,
                Screens.GlobalSearch
            )

            else -> listOf()
        }

}
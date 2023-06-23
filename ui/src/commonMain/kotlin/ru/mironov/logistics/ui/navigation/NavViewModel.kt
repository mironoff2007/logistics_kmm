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
import ru.mironov.logistics.ui.navigation.navcontroller.NavController

@Inject
class NavViewModel(
    val prefs: SharedPreferences,
    val logger: Logger
) : ViewModel() {

    lateinit var navigationController: NavController
    fun setNavController(navController: NavController) {
        this.navigationController = navController
    }

    fun navigate(route: String) {
        args = null
        navigationController.navigate(route)
    }

    fun setStartDestination(screen: Screens, vm: ViewModel) {
        navigationController.setStartDestination(screen, vm)
    }

    private var args: String? = null

    fun getArgs(): String? {
        val argsToReturn = args
        args = null
        return argsToReturn
    }

    fun navigateWithArgs(route: String, json: String) {
        args = json
        navigationController.navigate(route)
    }

    fun navigateBack() {
        args = null
        navigationController.navigateBack()
    }

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
    fun enableLoggerIfEnabled() {
        viewModelScope.launch {
            val settings = prefs.load() ?: CommonSettings()
            val enabled = settings.getBool(CommonSettings.LogsEnabled) ?: false
            if (enabled) logger.enable()
        }
    }

    fun getAllowedDestinationsFor(screen: Screens) {
        viewModelScope.launch {
            val list = when (screen) {
                Screens.LoginScreen, Screens.LogoutScreen -> listOf(Screens.SettingsScreenLoggedOut)
                Screens.RegisterSenderParcel, Screens.Warehouse, Screens.SettingsScreen -> listOf(
                    Screens.RegisterSenderParcel,
                    Screens.Warehouse,
                    Screens.SettingsScreen,
                    Screens.LogoutScreen
                )
                Screens.RegisterDestinationParcel, Screens.RegisterResult -> listOf(Screens.Back)
                Screens.SettingsScreenLoggedOut -> listOf(Screens.LogoutScreen)
                Screens.ParcelData -> listOf(Screens.Back)
                else -> listOf()
            }
            _allowedDestinations.emit(list)
        }
    }

}
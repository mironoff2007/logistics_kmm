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
class Navigator() {

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

}
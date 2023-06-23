package ru.mironov.logistics.ui.navigation.navcontroller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.mironov.domain.viewmodel.ViewModel
import ru.mironov.logistics.ui.navigation.Screens
import ru.mironov.logistics.ui.navigation.ViewModelFactory

/**
 * NavController Class
 */
class NavController(
    private val startDestination: String,
    private var backStackScreens: MutableSet<String> = mutableSetOf(),
    private val factory: ViewModelFactory
) {
    var root: MutableState<String> = mutableStateOf(startDestination)
    // Variable to store the state of the current screen
    var currentScreen: MutableState<String> = mutableStateOf(startDestination)

    // Function to handle the navigation between the screen
    fun navigate(route: String) {
        if (route != currentScreen.value) {
            if (root.value == route) {
                backStackScreens.clear()
                factory.removeAll()
            }
            if (backStackScreens.contains(currentScreen.value) && currentScreen.value != root.value) {
                backStackScreens.remove(currentScreen.value)
            }

            if (route == root.value) {
                backStackScreens = mutableSetOf()
                factory.removeAll()
            } else {
                backStackScreens.add(currentScreen.value)
            }

            currentScreen.value = route
        }
    }

    // Function to handle the back
    fun navigateBack() {
        if (backStackScreens.isNotEmpty()) {
            currentScreen.value = backStackScreens.last()
            backStackScreens.remove(currentScreen.value)
            factory.delete()
        }
    }

    @Serializable
    data class NavControllerData(
        val root: String,
        val currentScreen: String,
        val backStackScreens: MutableSet<String> = mutableSetOf()
    )

    fun getData(): NavControllerData {
        return NavControllerData(root.value, currentScreen.value, backStackScreens)
    }

    fun setStartDestination(screen: Screens, vm: ViewModel) {
        factory.removeAll(except = vm::class)
        backStackScreens.clear()
        root.value = screen.getName()
    }

    fun clearStack() {
        backStackScreens.clear()
    }

}


/**
 * Composable to remember the state of the navcontroller
 */

fun stateSaver(factory: ViewModelFactory) = Saver<MutableState<NavController>, Any>(
    save = { state ->
        val data = state.value.getData()
        val dataToSave = NavController.NavControllerData(
            data.root,
            state.value.currentScreen.value,
            data.backStackScreens
        )
        Json.encodeToString(dataToSave)
    },
    restore = { value ->
        val data = (value as? String)?.let {
            Json.decodeFromString<NavController.NavControllerData>(it)
        }
        mutableStateOf(
            NavController(
                data?.root ?: "",
                data?.backStackScreens ?: mutableSetOf(),
                factory
            ).apply { currentScreen = mutableStateOf(data?.currentScreen ?: "") }
        )
    }
)

@Composable
fun rememberNavController(
    startDestination: String,
    backStackScreens: MutableSet<String> = mutableSetOf(),
    viewModelFactory: ViewModelFactory,
): MutableState<NavController> = rememberSaveable(saver = stateSaver(viewModelFactory)) {
    mutableStateOf(
        NavController(
            startDestination,
            backStackScreens,
            viewModelFactory
        )
    )
}




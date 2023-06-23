package ru.mironov.logistics.ui.navigation.navcontroller

import androidx.compose.runtime.Composable
import ru.mironov.logistics.ui.navigation.NavViewModel
import ru.mironov.logistics.ui.navigation.Screens

/**
 * NavigationHost class
 */
class NavigationHost(
    val navController: NavController,
    val contents: @Composable NavigationGraphBuilder.() -> Unit
) {

    @Composable
    fun build() {
        NavigationGraphBuilder().renderContents()
    }

    inner class NavigationGraphBuilder(
        val navController: NavController = this@NavigationHost.navController
    ) {
        @Composable
        fun renderContents() {
            this@NavigationHost.contents(this)
        }
    }
}


@Composable
fun NavigationHost.NavigationGraphBuilder.composableRoute(
    route: Screens,
    navModel: NavViewModel,
    content: @Composable (screen: Screens) -> Unit
) {
    val routeName = route.getName()
    if (navController.currentScreen.value == routeName) {
        navModel.getAllowedDestinationsFor(route)
        content.invoke(route)
    }
}
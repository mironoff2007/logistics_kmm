import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.mironov.logistics.ui.navigation.Drawer
import ru.mironov.logistics.ui.navigation.NavViewModel
import ru.mironov.logistics.ui.navigation.Navigator

import ru.mironov.logistics.ui.navigation.Screens
import ru.mironov.logistics.ui.navigation.ViewModelFactory
import ru.mironov.logistics.ui.navigation.navcontroller.NavigationHost
import ru.mironov.logistics.ui.navigation.navcontroller.composableRoute
import ru.mironov.logistics.ui.navigation.navcontroller.rememberNavController
import ru.mironov.logistics.ui.screens.login.LoginScreen
import ru.mironov.logistics.ui.screens.login.LoginViewModel
import ru.mironov.logistics.ui.screens.parceldata.ParcelDataScreen
import ru.mironov.logistics.ui.screens.parceldata.ParcelDataViewModel
import ru.mironov.logistics.ui.screens.registerparceldestination.RegisterParcelDestinationScreen
import ru.mironov.logistics.ui.screens.registerparceldestination.RegisterParcelDestinationViewModel
import ru.mironov.logistics.ui.screens.registerparcelsender.RegisterParcelSenderScreen
import ru.mironov.logistics.ui.screens.registerparcelsender.RegisterParcelSenderViewModel
import ru.mironov.logistics.ui.screens.registerresult.RegisterResult
import ru.mironov.logistics.ui.screens.registerresult.RegisterResultViewModel
import ru.mironov.logistics.ui.screens.settings.SettingsScreen
import ru.mironov.logistics.ui.screens.settings.SettingsViewModel
import ru.mironov.logistics.ui.screens.splash.SplashScreen
import ru.mironov.logistics.ui.screens.splash.SplashViewModel
import ru.mironov.logistics.ui.screens.store.StoreScreen
import ru.mironov.logistics.ui.screens.store.StoreViewModel


@Composable
fun NavRoot(
    navigator: Navigator,
    factory: ViewModelFactory,
    backPressed: (() -> Unit) -> Unit
) {

    val navController by rememberNavController(startDestination = Screens.SplashScreen.getName(), viewModelFactory = factory)

    MaterialTheme {

        val navViewModel = factory.provide<NavViewModel>(type = NavViewModel::class, keepForever =  true)

        navigator.setNavController(navController)

        val allowedDestinations by navViewModel.allowedDestinations.collectAsState()
        val showMsgFlag by navViewModel.showMsg.collectAsState()

        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background),
            elevation = 0.dp
        ) {
            var size by remember { mutableStateOf(IntSize.Zero) }
            val drawerWidth = if (size.width > size.height) 0.35f * size.width
            else 0.75f * size.width
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val openDrawer = { scope.launch { drawerState.open() } }
            ModalDrawer(
                modifier = Modifier.fillMaxSize().onSizeChanged { size = it },
                drawerState = drawerState,
                gesturesEnabled = drawerState.isOpen,
                drawerContent = {
                    Drawer(
                        drawerWidth = drawerWidth,
                        onDestinationClicked = { route ->
                            if (navController.currentScreen.value != route) {
                                if (route == Screens.Back.getName()) navController.navigateBack()
                                else {
                                    navController.navigate(route)
                                    navController.clearStack()
                                    factory.removeAll()
                                }
                            }
                            scope.launch { drawerState.close() }
                        },
                        allowedDestinations = allowedDestinations,
                    )
                },
                drawerShape = customShape(drawerWidth, size.height * 1f),

                ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    val showMsg = fun(msg: String) { navViewModel.showMsg(msg, 3000) }

                    CustomNavigationHost(
                        viewModelFactory = factory,
                        openDrawer = openDrawer,
                        navModel = navViewModel,
                        navigator = navigator,
                        backPressed = backPressed,
                        showMsg = showMsg
                    )

                    showMsgFlag?.let { msg -> Dialog(msg)  }
                }
            }
        }
    }
}

fun customShape(drawerWidth: Float, height: Float) = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Rectangle(Rect(0f, 0f, drawerWidth, height))
}

@Composable
fun CustomNavigationHost(
    openDrawer: () -> Job,
    viewModelFactory: ViewModelFactory,
    navigator: Navigator,
    navModel: NavViewModel,
    backPressed: (() -> Unit) -> Unit,
    showMsg: (String) -> Unit
) {
    NavigationHost(navigator.navigationController) {

        composableRoute(Screens.SplashScreen, navModel) {
            val vm = viewModelFactory.provide<SplashViewModel>(SplashViewModel::class)
            SplashScreen(navigator, vm)
        }
        composableRoute(Screens.LoginScreen, navModel) {
            val vm: LoginViewModel = viewModelFactory.provide(LoginViewModel::class)
            navigator.setStartDestination(it, vm)
            LoginScreen(openDrawer, vm, navigator, showMsg)
        }
        composableRoute(Screens.LogoutScreen, navModel) {
            val vm: LoginViewModel = viewModelFactory.provide(LoginViewModel::class)
            navigator.setStartDestination(it, vm)
            vm.logout()
            LoginScreen(openDrawer, vm, navigator, showMsg)
        }
        composableRoute(Screens.RegisterSenderParcel, navModel) {
            val vm: RegisterParcelSenderViewModel = viewModelFactory.provide(RegisterParcelSenderViewModel::class)
            navigator.setStartDestination(it, vm)
            RegisterParcelSenderScreen(openDrawer, vm, navigator)
        }
        composableRoute(Screens.RegisterDestinationParcel, navModel) {
            val vm: RegisterParcelDestinationViewModel = viewModelFactory.provide(RegisterParcelDestinationViewModel::class)
            RegisterParcelDestinationScreen(openDrawer, vm, navigator, backPressed)
        }
        composableRoute(Screens.RegisterResult, navModel) {
            val vm: RegisterResultViewModel = viewModelFactory.provide(RegisterResultViewModel::class)
            RegisterResult(openDrawer, backPressed, vm, navigator, showMsg)
        }
        composableRoute(Screens.CarCargo, navModel) { screen ->
            val vm: StoreViewModel = viewModelFactory.provide(StoreViewModel::class)
            navigator.setStartDestination(screen, vm)
            StoreScreen(openDrawer, vm, navigator, screen)
        }
        composableRoute(Screens.Warehouse, navModel) { screen ->
            val vm: StoreViewModel = viewModelFactory.provide(StoreViewModel::class)
            navigator.setStartDestination(screen, vm)
            StoreScreen(openDrawer, vm, navigator, screen)
        }
        composableRoute(Screens.BackPack, navModel) { screen ->
            val vm: StoreViewModel = viewModelFactory.provide(StoreViewModel::class)
            navigator.setStartDestination(screen, vm)
            StoreScreen(openDrawer, vm, navigator, screen)
        }
        composableRoute(Screens.GlobalSearch, navModel) { screen ->
            val vm: StoreViewModel = viewModelFactory.provide(StoreViewModel::class)
            navigator.setStartDestination(screen, vm)
            StoreScreen(openDrawer, vm, navigator, screen)
        }
        composableRoute(Screens.ParcelData, navModel) {
            val vm: ParcelDataViewModel = viewModelFactory.provide(ParcelDataViewModel::class)
            ParcelDataScreen(openDrawer, vm, navigator, backPressed)
        }
        composableRoute(Screens.SettingsScreen, navModel) {
            val vm: SettingsViewModel = viewModelFactory.provide(SettingsViewModel::class)
            navigator.setStartDestination(it, vm)
            SettingsScreen(openDrawer, vm)
        }
        composableRoute(Screens.SettingsScreenLoggedOut, navModel) {
            val vm: SettingsViewModel = viewModelFactory.provide(SettingsViewModel::class)
            navigator.setStartDestination(it, vm)
            SettingsScreen(openDrawer, vm)
        }
    }.build()
}

@Composable
fun Dialog(showMsg: String) {
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(15.dp),
        elevation = 10.dp,
        color = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(
            modifier = Modifier, contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier.padding(15.dp),
                text = AnnotatedString(showMsg),
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.h4.fontFamily,
                    fontSize = 25.sp,
                    color = MaterialTheme.colors.onSurface
                )
            )
        }
    }
}
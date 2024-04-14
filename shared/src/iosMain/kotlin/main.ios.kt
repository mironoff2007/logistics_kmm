import androidx.compose.ui.window.ComposeUIViewController
import com.mironov.di.ApplicationComponent
import ru.mironov.common.ui.theme.LogisticsTheme
import ru.mironov.logistics.ui.navigation.Navigator

fun MainViewController() = ComposeUIViewController {
    LogisticsTheme {
        val navigator = Navigator()
        NavRoot(navigator, ApplicationComponent.getVmFactory()) {}
    }
}
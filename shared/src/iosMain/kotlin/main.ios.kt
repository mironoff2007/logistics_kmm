import androidx.compose.ui.window.ComposeUIViewController
import com.mironov.di.ApplicationComponent
import ru.mironov.logistics.ui.App2
import ru.mironov.logistics.ui.theme.LogisticsTheme

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController {
    LogisticsTheme {
        NavRoot(ApplicationComponent.getVmFactory()) {}
    }
}
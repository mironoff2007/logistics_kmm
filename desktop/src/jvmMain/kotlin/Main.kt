
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mironov.DesktopAppCommon
import com.mironov.di.ApplicationComponent
import ru.mironov.common.ui.theme.LogisticsTheme


fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        LogisticsTheme {
            NavRoot(ApplicationComponent.getVmFactory()) {}
        }
    }
}

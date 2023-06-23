import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mironov.di.ApplicationComponent
import ru.mironov.logistics.ui.theme.LogisticsTheme


fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        LogisticsTheme {
            NavRoot(ApplicationComponent.getVmFactory()) {}
        }
    }
}

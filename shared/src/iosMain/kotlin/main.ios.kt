import androidx.compose.ui.window.ComposeUIViewController
import com.mironov.di.ApplicationComponent
import ru.mironov.common.ui.theme.LogisticsTheme

fun MainViewController() = ComposeUIViewController {
    LogisticsTheme {
        NavRoot(ApplicationComponent.getVmFactory()) {}
    }
}
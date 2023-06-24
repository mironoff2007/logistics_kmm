import androidx.compose.ui.window.ComposeUIViewController
import ru.mironov.logistics.ui.App2

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { App2() }
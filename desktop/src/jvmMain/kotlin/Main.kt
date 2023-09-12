
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mironov.DesktopAppCommon

/**
 * Run only by gradle task
 * Run by play button on 'main' does not work
 */
fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        DesktopAppCommon()
    }
}

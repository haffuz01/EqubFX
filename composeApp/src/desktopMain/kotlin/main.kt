import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val state = rememberWindowState(
        size = DpSize(900.dp, 700.dp),
        position = WindowPosition(100.dp, 0.dp)
    )
    Window(onCloseRequest = ::exitApplication, title = "TheEqub", state = state) {
        App()
    }
}
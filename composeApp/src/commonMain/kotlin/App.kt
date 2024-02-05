import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import app.initKoin
import cafe.adriel.voyager.navigator.Navigator
import ui.screens.detail.WeatherDetailScreen

@Composable
fun App() {
    initKoin()
    MaterialTheme {
        Navigator(WeatherDetailScreen)
    }
}
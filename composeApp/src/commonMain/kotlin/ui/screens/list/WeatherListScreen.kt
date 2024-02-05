package ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import data.models.WeatherForecastData
import ui.composables.WeatherListSection

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 23:25
 * USER    : mambo
 */

class WeatherListScreen(val forecasts: List<WeatherForecastData>) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel: WeatherListScreenModel = getScreenModel()
        val state by screenModel.state.collectAsState()

        screenModel.updateFutureList(list = forecasts)

        LaunchedEffect(Unit) {
            screenModel.getHistoryWeatherForecast()
        }

        WeatherListScreenContent(
            state = state,
            onClickNavigateBack = { navigator?.pop() }
        )
    }
}

@Composable
fun WeatherListScreenContent(
    state: WeatherListScreenState,
    onClickNavigateBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = MaterialTheme.colors.background,
                title = {},
                navigationIcon = {
                    IconButton(onClick = onClickNavigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize()
        ) {
            WeatherListSection(
                modifier = Modifier.fillMaxWidth().weight(1f),
                title = "Past 14 Days",
                state = state.historyState,
                isReversed = true
            )
            WeatherListSection(
                modifier = Modifier.fillMaxWidth(),
                title = "Today",
                state = state.todayState
            )
            WeatherListSection(
                modifier = Modifier.fillMaxWidth().weight(1f),
                title = "Next 7 Days",
                state = state.futureState
            )
        }

    }

}

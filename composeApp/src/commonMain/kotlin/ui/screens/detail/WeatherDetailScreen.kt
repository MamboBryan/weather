package ui.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.composables.CenteredColumn
import ui.composables.CircularProgressBar
import ui.composables.LeftIconWithText
import ui.composables.RightIconWithText
import ui.composables.WeatherHourItem
import ui.composables.WeatherInfoItem
import ui.helpers.LoadState

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 16:33
 * USER    : mambo
 */

object WeatherDetailScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val screenModel: WeatherDetailScreenModel = getScreenModel()
        val state by screenModel.state.collectAsState()

        LaunchedEffect(Unit) {
            screenModel.getCurrentWeatherForecast()
        }

        WeatherDetailScreenContent(
            state = state,
            onClickRetry = screenModel::getCurrentWeatherForecast,
            onClickNavigateToNext7Days = screenModel::navigateToNext7Days,
            onClickNavigateToLast14Days = screenModel::navigateToLast14Days
        )
    }
}

@Composable
fun WeatherDetailScreenContent(
    state: WeatherDetailScreenState,
    onClickRetry: () -> Unit,
    onClickNavigateToNext7Days: () -> Unit,
    onClickNavigateToLast14Days: () -> Unit
) {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nairobi",
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            }
            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                AnimatedContent(targetState = state.weatherData) { value ->
                    when (value) {
                        LoadState.Loading -> {
                            CenteredColumn(modifier = Modifier.fillMaxSize()) {
                                CircularProgressBar(
                                    modifier = Modifier.size(50.dp),
                                    strokeWidth = 7.dp
                                )
                            }
                        }

                        is LoadState.Error -> {
                            CenteredColumn(modifier = Modifier.fillMaxSize()) {
                                Text(text = "Error", fontWeight = FontWeight.Bold, fontSize = 36.sp)
                                Text(
                                    modifier = Modifier.fillMaxWidth(0.6f)
                                        .padding(top = 8.dp, bottom = 24.dp),
                                    text = value.message,
                                    textAlign = TextAlign.Center,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                                TextButton(onClick = onClickRetry) {
                                    Text(text = "retry")
                                }
                            }
                        }

                        is LoadState.Success -> {
                            val result = value.data
                            CenteredColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Card(
                                        modifier = Modifier.size(400.dp).padding(16.dp),
                                        backgroundColor = MaterialTheme.colors.primary
                                    ) { }
                                    Text(
                                        text = "${result.day.averageTemperatureInCelsius}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 80.sp
                                    )
                                    Text(
                                        text = result.day.condition.label,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 28.sp
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    WeatherInfoItem(
                                        title = "Wind",
                                        value = "${result.day.maxWindInKilometersPerHour}",
                                        unit = "kph"
                                    )
                                    WeatherInfoItem(
                                        title = "Humidity",
                                        value = "${result.day.averageHumidity}",
                                        unit = "%"
                                    )
                                    WeatherInfoItem(
                                        title = "Rain",
                                        value = "${result.day.chancesOfRainInPercentage}",
                                        unit = "%"
                                    )
                                }
                                Divider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.25f)
                                )
                                LazyRow {
                                    items(result.hours) { hour ->
                                        WeatherHourItem(
                                            item = hour,
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = state.weatherData is LoadState.Success
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LeftIconWithText(
                        text = "Last Week",
                        icon = Icons.Rounded.ArrowBack,
                        onClick = onClickNavigateToLast14Days
                    )
                    RightIconWithText(
                        text = "Next Week",
                        icon = Icons.Rounded.ArrowForward,
                        onClick = onClickNavigateToNext7Days
                    )
                }
            }
        }
    }
}


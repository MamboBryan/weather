package ui.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.composables.CenteredColumn
import ui.composables.CircularProgressBar
import ui.composables.WeatherHourItem
import ui.composables.WeatherInfoItem
import ui.helpers.LoadState
import ui.screens.list.WeatherListScreen

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 16:33
 * USER    : mambo
 */

object WeatherDetailScreen : Screen {

    data object Tags {
        const val Loading = "WeatherDetail.Loading"
        const val Error = "WeatherDetail.Error"
        const val ErrorMessage = "WeatherDetail.Error.Message"
        const val Success = "WeatherDetail.Success"
        const val ShowMore = "WeatherDetail.ShowMore"
    }

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val screenModel: WeatherDetailScreenModel = getScreenModel()
        val state by screenModel.state.collectAsState()

        LaunchedEffect(Unit) {
            screenModel.getCurrentWeatherForecast()
        }

        LaunchedEffect(state.navigateToShowMore) {
            if (state.navigateToShowMore) {
                navigator?.push(WeatherListScreen(forecasts = state.forecasts))
                screenModel.resetNavigation()
            }
        }

        WeatherDetailScreenContent(
            state = state,
            onClickRetry = screenModel::getCurrentWeatherForecast,
            onClickShowMore = screenModel::onClickShowMore,
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WeatherDetailScreenContent(
    state: WeatherDetailScreenState,
    onClickRetry: () -> Unit,
    onClickShowMore: () -> Unit,
) {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                AnimatedContent(targetState = state.weatherData) { value ->
                    when (value) {
                        LoadState.Loading -> {
                            CenteredColumn(
                                modifier = Modifier.fillMaxSize()
                                    .testTag(WeatherDetailScreen.Tags.Loading)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Image(
                                        modifier = Modifier.size(150.dp).padding(bottom = 16.dp),
                                        painter = painterResource("images/icon.png"),
                                        contentDescription = "icon"
                                    )
                                    CircularProgressBar(
                                        modifier = Modifier.size(50.dp),
                                        strokeWidth = 7.dp
                                    )
                                }
                            }
                        }

                        is LoadState.Error -> {
                            CenteredColumn(
                                modifier = Modifier.fillMaxSize()
                                    .testTag(WeatherDetailScreen.Tags.Error)
                            ) {
                                Text(text = "Error", fontWeight = FontWeight.Bold, fontSize = 36.sp)
                                Text(
                                    modifier = Modifier
                                        .testTag(WeatherDetailScreen.Tags.ErrorMessage)
                                        .fillMaxWidth(0.6f)
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
                            CenteredColumn(
                                modifier = Modifier.testTag(WeatherDetailScreen.Tags.Success)
                                    .fillMaxSize()
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Column(
                                        modifier = Modifier,
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Berlin",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 36.sp
                                        )
                                        Text(
                                            text = buildString {
                                                append(
                                                    result.date.dayOfWeek.name.lowercase()
                                                        .replaceFirstChar { it.uppercase() }
                                                )
                                                append(",")
                                                append(result.date.dayOfMonth)
                                                append(" ")
                                                append(
                                                    result.date.month.name.lowercase()
                                                        .replaceFirstChar { it.uppercase() }
                                                )
                                            }
                                        )
                                    }
                                    Card(
                                        modifier = Modifier
                                            .size(200.dp)
                                            .padding(16.dp),
                                        elevation = 0.dp
                                    ) {
                                        val painter =
                                            asyncPainterResource(data = "https:" + result.day.condition.iconUrl)
                                        KamelImage(
                                            modifier = Modifier.fillMaxSize(),
                                            resource = painter,
                                            contentScale = ContentScale.Crop,
                                            contentDescription = "Profile",
                                        )
                                    }
                                    Text(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 80.sp,
                                        text = buildAnnotatedString {
                                            append("${result.day.averageTemperatureInCelsius.toInt()}")
                                            withStyle(
                                                style = SpanStyle(
                                                    fontSize = 48.sp,
                                                    baselineShift = BaselineShift.Superscript
                                                )
                                            ) {
                                                append("o")
                                            }
                                            append("C")
                                        }
                                    )
                                    Text(
                                        text = result.day.condition.label,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 28.sp
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(24.dp),
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
                                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f)
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
                    horizontalArrangement = Arrangement.Center
                ) {

                    TextButton(
                        modifier = Modifier.testTag(WeatherDetailScreen.Tags.ShowMore),
                        onClick = onClickShowMore
                    ) {
                        Text(text = "Show More")
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }

                }
            }
        }
    }
}


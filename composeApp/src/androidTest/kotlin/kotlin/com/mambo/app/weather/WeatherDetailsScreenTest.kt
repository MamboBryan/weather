package kotlin.com.mambo.app.weather

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import data.models.WeatherConditionData
import data.models.WeatherDayData
import data.models.WeatherForecastData
import data.models.WeatherHourData
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.Rule
import org.junit.Test
import ui.helpers.LoadState
import ui.screens.detail.WeatherDetailScreen
import ui.screens.detail.WeatherDetailScreenContent
import ui.screens.detail.WeatherDetailScreenState

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 03:24
 * USER    : mambo
 */

class WeatherDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowLoadingOnInitialization() {
        composeTestRule.setContent {
            MaterialTheme {
                WeatherDetailScreenContent(
                    state = WeatherDetailScreenState(),
                    onClickRetry = {},
                    onClickNavigateToLast14Days = {},
                    onClickNavigateToNext7Days = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(WeatherDetailScreen.Tags.Loading).assertIsDisplayed()
    }

    @Test
    fun whenErrorIsReceivedShouldShowErrorComposable() {
        val message = "Fetching error"
        composeTestRule.setContent {
            MaterialTheme {
                WeatherDetailScreenContent(
                    state = WeatherDetailScreenState(weatherData = LoadState.Error(message = message)),
                    onClickRetry = {},
                    onClickNavigateToLast14Days = {},
                    onClickNavigateToNext7Days = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(WeatherDetailScreen.Tags.Error).assertIsDisplayed()
        composeTestRule.onNodeWithTag(WeatherDetailScreen.Tags.ErrorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText(message).assertIsDisplayed()
    }

    @Test
    fun whenSuccessfulShouldShowSuccessComposableData() {
        val data = WeatherForecastData(
            date = LocalDate(2024, 2, 3),
            day = WeatherDayData(
                maxTemperatureInCelsius = 35.0,
                minTemperatureInCelsius = 24.0,
                averageTemperatureInCelsius = 29.5,
                averageHumidity = 24.0,
                maxWindInKilometersPerHour = 40.0,
                willRain = false,
                chancesOfRainInPercentage = 25.0,
                condition = WeatherConditionData("Sunny", "")
            ),
            hours = listOf(
                WeatherHourData(
                    time = LocalDateTime(2024, 2, 3, 6, 30, 0, 0),
                    temperatureInCelsius = 25.0,
                    isDaylight = true,
                    condition = WeatherConditionData("Sunny", "")
                )
            )
        )
        composeTestRule.setContent {
            MaterialTheme {
                WeatherDetailScreenContent(
                    state = WeatherDetailScreenState(weatherData = LoadState.Success(data = data)),
                    onClickRetry = {},
                    onClickNavigateToLast14Days = {},
                    onClickNavigateToNext7Days = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(WeatherDetailScreen.Tags.Success).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.day.condition.label).assertIsDisplayed()
    }

}
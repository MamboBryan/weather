package ui.screens

import data.models.WeatherConditionData
import data.models.WeatherDayData
import data.models.WeatherForecastData
import data.models.WeatherHourData
import data.repositories.FakeWeatherRepository
import domain.usecases.GetHistoryWeatherDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import ui.helpers.LoadState
import ui.screens.list.WeatherListScreenModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 21:57
 * USER    : mambo
 */

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherListScreenModelTest {

    private lateinit var repository: FakeWeatherRepository
    private lateinit var getHistoryWeatherDataUseCase: GetHistoryWeatherDataUseCase
    private lateinit var weatherListScreenModel: WeatherListScreenModel

    private val data = WeatherForecastData(
        date = LocalDate(2024, 1, 1),
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

    @BeforeTest
    fun before() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        repository = FakeWeatherRepository()
        getHistoryWeatherDataUseCase = GetHistoryWeatherDataUseCase(weatherRepository = repository)
        weatherListScreenModel =
            WeatherListScreenModel(getHistoryWeatherDataUseCase = getHistoryWeatherDataUseCase)
    }

    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given WeatherListScreenModel - initial futureState should be Loading`() = runTest {
        val data = weatherListScreenModel.state.value.futureState
        assertTrue { data is LoadState.Loading }
    }

    @Test
    fun `given WeatherListScreenModel - when updating futureState - should be ListState Success`() =
        runTest {
            weatherListScreenModel.updateFutureList(listOf())
            val data = weatherListScreenModel.state.value.futureState
            assertTrue { data is LoadState.Success }
        }

    @Test
    fun `given WeatherListScreenModel - when updating futureState with data - state should contain data`() =
        runTest {
            weatherListScreenModel.updateFutureList(listOf(data))
            val data = weatherListScreenModel.state.value.futureState
            val value = (data as LoadState.Success).data
            assertTrue { value.isNotEmpty() }
        }

    @Test
    fun `given WeatherListScreenModel - when fetching history data is not successful - historyState should be Error`() =
        runTest {
            repository.simulateError()
            weatherListScreenModel.getHistoryWeatherForecast()
            val data = weatherListScreenModel.state.value.historyState
            assertTrue { data is LoadState.Error }
        }

    @Test
    fun `given WeatherListScreenModel - when fetching history data is successful - historyState should be Success`() =
        runTest {
            repository.simulateSuccess()
            weatherListScreenModel.getHistoryWeatherForecast()
            val data = weatherListScreenModel.state.value.historyState
            assertTrue { data is LoadState.Success }
        }

    @Test
    fun `given WeatherListScreenModel - when fetching history data is successful - historyState should be Success and not empty`() =
        runTest {
            repository.simulateSuccess()
            weatherListScreenModel.getHistoryWeatherForecast()
            val data = weatherListScreenModel.state.value.historyState
            assertTrue { data is LoadState.Success }
            val value = (data as LoadState.Success).data
            assertTrue { value.isNotEmpty() }
        }

}
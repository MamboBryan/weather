package ui.screens

import data.repositories.FakeWeatherRepository
import domain.usecases.GetCurrentWeatherDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import ui.helpers.LoadState
import ui.screens.detail.WeatherDetailScreenModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 21:57
 * USER    : mambo
 */

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherDetailsScreenModelTest {

    private lateinit var repository: FakeWeatherRepository
    private lateinit var getCurrentWeatherDataUseCase: GetCurrentWeatherDataUseCase
    private lateinit var weatherDetailScreenModel: WeatherDetailScreenModel

    @BeforeTest
    fun before() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        repository = FakeWeatherRepository()
        getCurrentWeatherDataUseCase = GetCurrentWeatherDataUseCase(weatherRepository = repository)
        weatherDetailScreenModel =
            WeatherDetailScreenModel(getCurrentWeatherDataUseCase = getCurrentWeatherDataUseCase)
    }

    @AfterTest
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given WeatherDetailScreenModel - initial state should be Loading`() = runTest {
        val data = weatherDetailScreenModel.state.value.weatherData
        assertTrue { data is LoadState.Loading }
    }

    @Test
    fun `given WeatherDetailScreenModel - when fetching data is successful - load state should be Success`() =
        runTest {
            repository.simulateSuccess()
            weatherDetailScreenModel.getCurrentWeatherForecast()
            val data = weatherDetailScreenModel.state.value.weatherData
            assertTrue { data is LoadState.Success }
        }

    @Test
    fun `given WeatherDetailScreenModel - when fetching data is not successful - load state should be Error`() =
        runTest {
            repository.simulateError()
            weatherDetailScreenModel.getCurrentWeatherForecast()
            val data = weatherDetailScreenModel.state.value.weatherData
            assertTrue { data is LoadState.Error }
        }

    @Test
    fun `given WeatherDetailScreenModel - when navigation to next 7 days - should be true`() =
        runTest {
            weatherDetailScreenModel.navigateToNext7Days()
            val value = weatherDetailScreenModel.state.value.navigateToNext7Days
            assertTrue { value }
        }

    @Test
    fun `given WeatherDetailScreenModel - when navigation to last 14 days - should be true`() =
        runTest {
            weatherDetailScreenModel.navigateToLast14Days()
            val value = weatherDetailScreenModel.state.value.navigateToLast14Days
            assertTrue { value }
        }

    @Test
    fun `given WeatherDetailScreenModel - when resetting navigation - should all be false`() =
        runTest {
            weatherDetailScreenModel.navigateToNext7Days()
            weatherDetailScreenModel.navigateToLast14Days()
            val value = weatherDetailScreenModel.state.value
            assertTrue { value.navigateToNext7Days }
            assertTrue { value.navigateToLast14Days }
            weatherDetailScreenModel.resetNavigation()
            val updatedValue = weatherDetailScreenModel.state.value
            assertFalse { updatedValue.navigateToNext7Days }
            assertFalse { updatedValue.navigateToLast14Days }
        }

}
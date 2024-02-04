package data.repositories

import data.extensions.today
import data.helpers.DataResult
import data.models.WeatherConditionData
import data.models.WeatherDayData
import data.models.WeatherForecastData
import data.models.WeatherHourData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.minus
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 15:32
 * USER    : mambo
 */

/**
 * A fake of [WeatherRepository] to use for testing
 */
class FakeWeatherRepository : WeatherRepository {

    private val data: MutableStateFlow<List<WeatherForecastData>?> = MutableStateFlow(emptyList())

    fun simulateError() {
        data.value = null
    }

    fun simulateSuccess() {
        data.value = listOf(
            WeatherForecastData(
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
        )
    }

    override suspend fun getCurrentWeatherData(): DataResult<List<WeatherForecastData>> {
        return when (val result = data.value) {
            null -> DataResult.Error(message = "error")
            else -> DataResult.Success(data = result)
        }
    }

    override suspend fun getPastWeatherData(
        startDate: LocalDate,
        endDate: LocalDate
    ): DataResult<List<WeatherForecastData>> {
        return when (val result = data.value) {
            null -> DataResult.Error(message = "error")
            else -> DataResult.Success(data = result)
        }
    }

}

/**
 * Test class for [WeatherRepository] to ensure it works as intended
 */
class WeatherRepositoryTest {

    private lateinit var repository: FakeWeatherRepository

    @BeforeTest
    fun setup() {
        repository = FakeWeatherRepository()
    }

    @Test
    fun `given WeatherRepository - when fetching weather data - should return DataResult Error`() =
        runTest {
            repository.simulateError()
            val data = repository.getCurrentWeatherData()
            assertTrue { data is DataResult.Error }
        }

    @Test
    fun `given WeatherRepository - when fetching weather data - should return DataResult Success`() =
        runTest {
            repository.simulateSuccess()
            val data = repository.getCurrentWeatherData()
            assertTrue { data is DataResult.Success }
        }

    @Test
    fun `given WeatherRepository - when fetching weather data - should not return an empty list`() =
        runTest {
            repository.simulateSuccess()
            val data = repository.getCurrentWeatherData()
            assertTrue { data is DataResult.Success }
            val list = (data as DataResult.Success).data
            assertTrue { list.isNotEmpty() }
        }

    @Test
    fun `given WeatherRepository - when fetching past weather data - should not return an empty list`() =
        runTest {
            repository.simulateSuccess()
            val data = repository.getPastWeatherData(
                startDate = today,
                endDate = today.minus(period = DatePeriod(days = 16))
            )
            assertTrue { data is DataResult.Success }
            val list = (data as DataResult.Success).data
            assertTrue { list.isNotEmpty() }
        }

    @Test
    fun `given WeatherRepository - when fetching past weather data - should return date less than today`() =
        runTest {
            repository.simulateSuccess()
            val data = repository.getPastWeatherData(
                startDate = today,
                endDate = today.minus(period = DatePeriod(days = 20))
            )
            assertTrue { data is DataResult.Success }
            val weather = (data as DataResult.Success).data.first()
            assertTrue { weather.date < today }
        }
}
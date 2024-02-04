package domain.usecases

import data.extensions.today
import data.helpers.DataResult
import data.repositories.FakeWeatherRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 22:33
 * USER    : mambo
 */

class GetWeatherHistoryUseCaseTest {

    private lateinit var getHistoryWeatherDataUseCase: GetHistoryWeatherDataUseCase
    private lateinit var weatherRepository: FakeWeatherRepository

    @BeforeTest
    fun setup() {
        weatherRepository = FakeWeatherRepository()
        getHistoryWeatherDataUseCase = GetHistoryWeatherDataUseCase(weatherRepository)
    }

    @Test
    fun `given GetHistoryWeatherDataUseCase - when fetching data - should return DataResult Success`() =
        runTest {
            weatherRepository.simulateSuccess()
            val data = getHistoryWeatherDataUseCase(startDate = today, endDate = today)
            assertTrue { data is DataResult.Success }
            val list = (data as DataResult.Success).data
            assertTrue { list.isNotEmpty() }
        }

    @Test
    fun `given GetHistoryWeatherDataUseCase - when fetching data - should return DataResult Error`() =
        runTest {
            weatherRepository.simulateError()
            val data = getHistoryWeatherDataUseCase(startDate = today, endDate = today)
            assertTrue { data is DataResult.Error }
            val message = (data as DataResult.Error).message
            assertEquals(expected = "error", actual = message)
        }

}
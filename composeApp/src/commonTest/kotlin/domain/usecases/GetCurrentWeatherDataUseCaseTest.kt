package domain.usecases

import data.helpers.DataResult
import data.repositories.FakeWeatherRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 16:09
 * USER    : mambo
 */

class GetCurrentWeatherDataUseCaseTest {

    private lateinit var getCurrentWeatherDataUseCase: GetCurrentWeatherDataUseCase
    private lateinit var weatherRepository: FakeWeatherRepository

    @BeforeTest
    fun setup() {
        weatherRepository = FakeWeatherRepository()
        getCurrentWeatherDataUseCase = GetCurrentWeatherDataUseCase(weatherRepository)
    }

    @Test
    fun `given GetCurrentWeatherDataUseCase - when fetching data - should return DataResult Success`() =
        runTest {
            weatherRepository.simulateSuccess()
            val data = getCurrentWeatherDataUseCase()
            assertTrue { data is DataResult.Success }
            val list = (data as DataResult.Success).data
            assertTrue { list.isNotEmpty() }
        }

    @Test
    fun `given GetCurrentWeatherDataUseCase - when fetching data - should return DataResult Error`() =
        runTest {
            weatherRepository.simulateError()
            val data = getCurrentWeatherDataUseCase()
            assertTrue { data is DataResult.Error }
            val message = (data as DataResult.Error).message
            assertEquals(expected = "error", actual = message)
        }

}
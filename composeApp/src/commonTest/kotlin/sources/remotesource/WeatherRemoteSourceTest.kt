package sources.remotesource

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import sources.remotesources.api.WeatherRemoteSource
import sources.remotesources.api.WeatherRemoteSourceImpl
import sources.remotesources.dtos.WeatherCurrentAndDaysForecastDTO
import sources.remotesources.helpers.NetworkResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 22:00
 * USER    : mambo
 */

class WeatherRemoteSourceTest {

    private val json = Json { ignoreUnknownKeys = true }

    private fun generateFakeUserRemoteSource(
        statusCode: HttpStatusCode,
        response: String
    ): WeatherRemoteSource = WeatherRemoteSourceImpl(
        client = generateFakeClient(engine = generateFakeEngine(statusCode, response))
    )

    @Test
    fun `given WeatherRemoteSource when fetching current forecast should return NetworkResultSuccess`() =
        runTest {
            val source = generateFakeUserRemoteSource(
                statusCode = HttpStatusCode.OK,
                response = VALID_RESPONSE_FORECAST
            )
            val response = source.fetchCurrentAndFutureWeatherForecast(city = "berlin")
            assertTrue { response is NetworkResult.Success }
        }

    @Test
    fun `given WeatherRemoteSource when fetching current forecast should return NetworkResultError`() =
        runTest {
            val source = generateFakeUserRemoteSource(
                statusCode = HttpStatusCode.OK,
                response = INVALID_RESPONSE_FORECAST
            )
            val response = source.fetchCurrentAndFutureWeatherForecast(city = "berlin")
            assertTrue { response is NetworkResult.Error}
        }

    @Test
    fun `given WeatherRemoteSource when fetching current forecast data should be correct`() =
        runTest {
            val source = generateFakeUserRemoteSource(
                statusCode = HttpStatusCode.OK,
                response = VALID_RESPONSE_FORECAST
            )
            val response = source.fetchCurrentAndFutureWeatherForecast(city = "berlin")
            val expected: WeatherCurrentAndDaysForecastDTO = json.decodeFromString(VALID_RESPONSE_FORECAST)

            assertTrue { response is NetworkResult.Success }
            val actual = (response as NetworkResult.Success).data
            assertEquals(expected = expected, actual = actual)
        }

}
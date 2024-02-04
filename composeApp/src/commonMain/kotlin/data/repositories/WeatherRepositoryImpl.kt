package data.repositories

import data.extensions.today
import data.helpers.DataResult
import data.mappers.fromDTO
import data.models.WeatherForecastData
import data.models.fromDTO
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import sources.remotesources.api.WeatherRemoteSource
import sources.remotesources.helpers.NetworkResult

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 01:25
 * USER    : mambo
 */

class WeatherRepositoryImpl(private val remoteSource: WeatherRemoteSource) : WeatherRepository {

    override suspend fun getCurrentWeatherData(): DataResult<List<WeatherForecastData>> {
        val result = remoteSource.fetchCurrentAndFutureWeatherForecast(
            city = "berlin",
            days = 8
        )
        return when (result) {
            is NetworkResult.Error -> DataResult.Error(message = result.message)
            is NetworkResult.Success -> {
                val data = result.data
                DataResult.Success(data = data.fromDTO())
            }
        }
    }

    override suspend fun getPastWeatherData(
        startDate: LocalDate,
        endDate: LocalDate
    ): DataResult<List<WeatherForecastData>> {
        val result = remoteSource.fetchPastWeatherDates(
            city = "berlin",
            startDate = today,
            endDate = today.minus(period = DatePeriod(days = 14))
        )
        return when (result) {
            is NetworkResult.Error -> DataResult.Error(message = result.message)
            is NetworkResult.Success -> DataResult.Success(data = result.data.forecast.forecastday.map { it.fromDTO() })
        }
    }

}
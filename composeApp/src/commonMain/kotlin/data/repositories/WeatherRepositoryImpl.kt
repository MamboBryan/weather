package data.repositories

import data.helpers.DataResult
import data.mappers.fromDTO
import data.models.WeatherForecastData
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

}
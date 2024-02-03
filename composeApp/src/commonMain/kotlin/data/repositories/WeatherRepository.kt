package data.repositories

import data.helpers.DataResult
import data.models.WeatherForecastData

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 23:39
 * USER    : mambo
 */

/**
 * Helper class for fetching data from the sources
 */
interface WeatherRepository {

    /**
     * Get today's and the next 7 days weather data
     * @return [DataResult] of [List] of [WeatherForecastData]
     */
    suspend fun getCurrentWeatherData(): DataResult<List<WeatherForecastData>>

}
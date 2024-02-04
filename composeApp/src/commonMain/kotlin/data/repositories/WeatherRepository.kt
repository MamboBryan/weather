package data.repositories

import data.helpers.DataResult
import data.models.WeatherForecastData
import kotlinx.datetime.LocalDate

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

    /**
     * Get past weather events within a certain date range
     * @param [start] the start date to search range
     * @param [end] the end date for the search range
     * @return [DataResult] of [List] of [WeatherForecastData]
     */
    suspend fun getPastWeatherData(start: LocalDate, end: LocalDate): DataResult<List<WeatherForecastData>>

}
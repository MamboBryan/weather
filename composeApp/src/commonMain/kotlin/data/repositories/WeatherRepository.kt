package data

import data.helper.DataResult
import data.models.WeatherData

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

    suspend fun getCurrentWeatherData(): DataResult<List<WeatherData>>

}
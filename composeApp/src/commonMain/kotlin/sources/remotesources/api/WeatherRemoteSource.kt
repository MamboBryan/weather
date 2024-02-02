package sources.remotesources.api

import sources.remotesources.dtos.WeatherCurrentAndDaysForecastDTO
import sources.remotesources.helpers.NetworkResult

/**
 * PROJECT : weather
 * DATE : Fri 02/02/2024
 * TIME : 13:46
 * USER : mambo
 */

/**
 * [WeatherRemoteSource] provides all the functions needed to interact with https://www.weatherapi.com/
 */
interface WeatherRemoteSource {

    /**
     * Fetches today's and future dates weather data based on [days]
     *
     * @param city the city weather data you want to fetch
     * @param days the number of days from today you'd like to fetch weather data for
     * @param isAirQualityEnabled condition to pass if you want to get air quality data
     * @param isAlertsEnabled condition to pass to get continue alerts
     * @return [WeatherCurrentAndDaysForecastDTO]
     */
    suspend fun fetchCurrentAndFutureWeatherForecast(
        city: String,
        days: Int = 7,
        isAirQualityEnabled: Boolean = false,
        isAlertsEnabled: Boolean = false
    ): NetworkResult<WeatherCurrentAndDaysForecastDTO>

}
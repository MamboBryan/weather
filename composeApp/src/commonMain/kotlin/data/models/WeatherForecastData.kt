package data.models

import kotlinx.datetime.LocalDate
import sources.remotesources.dtos.WeatherCurrentAndDaysForecastDTO

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 00:43
 * USER    : mambo
 */

data class WeatherData(
    val date: LocalDate,
    val day: WeatherDayData,
    val hours: List<WeatherHourData>
)

fun WeatherCurrentAndDaysForecastDTO.fromDTO(): List<WeatherData> {
    return forecast.forecastday.map { it.fromDTO() }
}
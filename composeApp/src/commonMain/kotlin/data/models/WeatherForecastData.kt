package data.models

import data.extensions.asLocalDate
import kotlinx.datetime.LocalDate
import sources.remotesources.dtos.WeatherForecastDTO

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 00:43
 * USER    : mambo
 */

data class WeatherForecastData(
    val date: LocalDate,
    val day: WeatherDayData,
    val hours: List<WeatherHourData>
)

fun WeatherForecastDTO.fromDTO() = WeatherForecastData(
    date = date.asLocalDate(),
    day = day.fromDTO(),
    hours = hour.map { it.fromDTO() }
)

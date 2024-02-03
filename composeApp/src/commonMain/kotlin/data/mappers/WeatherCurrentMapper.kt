package data.mappers

import data.models.WeatherForecastData
import data.models.fromDTO
import sources.remotesources.dtos.WeatherCurrentAndDaysForecastDTO

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 15:16
 * USER    : mambo
 */

fun WeatherCurrentAndDaysForecastDTO.fromDTO(): List<WeatherForecastData> {
    return forecast.forecastday.map { value -> value.fromDTO() }
}
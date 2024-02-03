package data.models

import data.extensions.asBoolean
import data.extensions.asLocalDateTime
import kotlinx.datetime.LocalDateTime
import sources.remotesources.dtos.WeatherForecastHourDTO

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 00:51
 * USER    : mambo
 */

/**
 * Weather data for a specific hour in a day
 */
data class WeatherHourData(
    val time: LocalDateTime,
    val temperatureInCelsius: Double,
    val isDaylight: Boolean,
    val condition: WeatherConditionData,
)

/**
 * Mapper for converting [WeatherForecastHourDTO] to [WeatherHourData]
 */
fun WeatherForecastHourDTO.fromDTO() = WeatherHourData(
    time = time.asLocalDateTime(),
    temperatureInCelsius = temp_c,
    isDaylight = is_day.asBoolean(),
    condition = condition.fromDTO()
)

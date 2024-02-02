package sources.remotesources.dtos

import kotlinx.serialization.Serializable

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 16:15
 * USER    : mambo
 */

/**
 * Weather data for the whole day with hour to hour stats
 */
@Serializable
data class WeatherDayDTO(
    val date: String,
    val day: WeatherForecastDayDTO,
    val hour: List<WeatherForecastHourDTO>
)

/**
 * Weather data for the day
 */
@Serializable
data class WeatherForecastDayDTO(
    val condition: WeatherConditionDTO
)

/**
 * Weather data per hour
 */
@Serializable
data class WeatherForecastHourDTO(
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Int,
    val condition: WeatherConditionDTO,
    val wind_kph: Double,
    val wind_mph: Double,
    val humidity: Double,
    val will_it_rain: Int,
)
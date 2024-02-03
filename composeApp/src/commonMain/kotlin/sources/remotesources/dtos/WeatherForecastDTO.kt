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


data class DayDTO(
    val avghumidity: Int,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avgvis_km: Int,
    val avgvis_miles: Int,
    val condition: Condition,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val daily_will_it_rain: Int,
    val daily_will_it_snow: Int,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val maxwind_mph: Double,
    val mintemp_c: Double,
    val mintemp_f: Int,
    val totalprecip_in: Int,
    val totalprecip_mm: Double,
    val totalsnow_cm: Int,
    val uv: Int
)

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)

package sources.remotesources.dtos

import kotlinx.serialization.Serializable

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 19:56
 * USER    : mambo
 */

@Serializable
data class WeatherCurrentForecastDTO(
    val last_updated: String,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Int,
    val condition: WeatherConditionDTO,
    val wind_kph: Int,
    val wind_mph: Int,
    val humidity: Int,
    val forecast: WeatherForecastHolderDTO
)


@Serializable
data class WeatherForecastHolderDTO(val forecastday: List<WeatherDayDTO>)
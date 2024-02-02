package sources.remotesources.dtos

import kotlinx.serialization.Serializable

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 19:53
 * USER    : mambo
 */

@Serializable
data class WeatherForecastDTO(
    val location: WeatherLocationDTO,
)
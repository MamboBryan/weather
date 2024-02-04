package sources.remotesources.dtos

import kotlinx.serialization.Serializable

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 21:37
 * USER    : mambo
 */

@Serializable
data class WeatherHistoryDTO(
    val forecast: WeatherForecastHolderDTO
)
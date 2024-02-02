package sources.remotesources.dtos

import kotlinx.serialization.Serializable

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 14:38
 * USER    : mambo
 */

@Serializable
data class WeatherDTO(
    val condition: WeatherConditionDTO
)
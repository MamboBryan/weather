package sources.remotesources.dtos

import kotlinx.serialization.Serializable

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 14:40
 * USER    : mambo
 */

@Serializable
data class WeatherConditionDTO(
    val text: String,
    val icon: String,
    val code: Long
)
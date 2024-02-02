package sources.remotesources.dtos

import kotlinx.serialization.Serializable

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 16:13
 * USER    : mambo
 */

@Serializable
data class WeatherLocation(
    val name: String,
    val region: String,
    val country: String
)
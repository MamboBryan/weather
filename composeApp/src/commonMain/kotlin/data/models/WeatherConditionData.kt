package data.models

import sources.remotesources.dtos.WeatherConditionDTO

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 00:45
 * USER    : mambo
 */

data class WeatherConditionData(
    val label: String,
    val iconUrl: String
)

fun WeatherConditionDTO.fromDTO() = WeatherConditionData(label = text, iconUrl = icon)
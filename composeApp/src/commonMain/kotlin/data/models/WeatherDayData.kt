package data.models

import data.extensions.asBoolean
import sources.remotesources.dtos.WeatherForecastDayDTO

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 00:47
 * USER    : mambo
 */

data class WeatherDayData(
    val maxTemperatureInCelsius: Double,
    val minTemperatureInCelsius: Double,
    val averageTemperatureInCelsius: Double,
    val maxWindInKilometersPerHour: Double,
    val averageHumidity: Double,
    val willRain: Boolean,
    val chancesOfRainInPercentage: Double,
    val condition: WeatherConditionData
)

fun WeatherForecastDayDTO.fromDTO() = WeatherDayData(
    maxTemperatureInCelsius = maxtemp_c,
    minTemperatureInCelsius = mintemp_c,
    averageTemperatureInCelsius = avgtemp_c,
    maxWindInKilometersPerHour = maxwind_kph,
    averageHumidity = avghumidity,
    willRain = daily_will_it_rain.asBoolean(),
    chancesOfRainInPercentage = daily_chance_of_rain,
    condition = condition.fromDTO()
)
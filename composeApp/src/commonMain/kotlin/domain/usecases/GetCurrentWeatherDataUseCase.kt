package domain.usecases

import data.helpers.DataResult
import data.models.WeatherForecastData
import data.repositories.WeatherRepository

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 16:06
 * USER    : mambo
 */

/**
 * Single use-case action to get current weather data
 */
class GetCurrentWeatherDataUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(): DataResult<List<WeatherForecastData>> {
        return weatherRepository.getCurrentWeatherData()
    }
}
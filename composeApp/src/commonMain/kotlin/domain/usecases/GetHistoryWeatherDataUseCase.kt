package domain.usecases

import data.helpers.DataResult
import data.models.WeatherForecastData
import data.repositories.WeatherRepository
import kotlinx.datetime.LocalDate

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 22:21
 * USER    : mambo
 */

/**
 * Single use-case class for getting weather forecast data from past dates
 * @param [weatherRepository]
 */
class GetHistoryWeatherDataUseCase(private val weatherRepository: WeatherRepository) {

    /**
     * Get weather data from past dates within a certain range
     * @param [startDate] start date for the range as [LocalDate]
     * @param [endDate] end date for the range as [LocalDate]
     * @return [DataResult] of [List] of [WeatherForecastData]
     */
    suspend operator fun invoke(
        startDate: LocalDate,
        endDate: LocalDate
    ): DataResult<List<WeatherForecastData>> {
        return weatherRepository.getPastWeatherData(startDate = startDate, endDate = endDate)
    }
}
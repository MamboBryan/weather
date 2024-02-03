package ui.screens.detail

import cafe.adriel.voyager.core.model.screenModelScope
import data.helpers.DataResult
import data.models.WeatherForecastData
import domain.usecases.GetCurrentWeatherDataUseCase
import kotlinx.coroutines.launch
import ui.helpers.LoadState
import ui.helpers.StatefulScreenModel

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 16:39
 * USER    : mambo
 */
data class WeatherDetailScreenState(
    val weatherData: LoadState<WeatherForecastData> = LoadState.Loading,
    val navigateToNext7Days: Boolean = false,
    val navigateToLast14Days: Boolean = false
)

class WeatherDetailScreenModel(
    private val getCurrentWeatherDataUseCase: GetCurrentWeatherDataUseCase,
) : StatefulScreenModel<WeatherDetailScreenState>(initial = WeatherDetailScreenState()) {

    fun getCurrentWeatherForecast() {
        screenModelScope.launch() {
            updateState { copy(weatherData = LoadState.Loading) }
            when (val result = getCurrentWeatherDataUseCase()) {
                is DataResult.Error -> updateState { copy(weatherData = LoadState.Error(message = result.message)) }
                is DataResult.Success -> updateState { copy(weatherData = LoadState.Success(data = result.data.first())) }
            }
        }
    }

    fun navigateToNext7Days() {
        screenModelScope.launch() {
            updateState { copy(navigateToNext7Days = true) }
        }
    }

    fun navigateToLast14Days() {
        screenModelScope.launch {
            updateState { copy(navigateToLast14Days = true) }
        }
    }

    fun resetNavigation() {
        updateState {
            copy(
                navigateToNext7Days = false,
                navigateToLast14Days = false
            )
        }
    }

}
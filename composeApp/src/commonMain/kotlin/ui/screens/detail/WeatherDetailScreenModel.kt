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
    val navigateToShowMore: Boolean = false,
    val forecasts: List<WeatherForecastData> = emptyList()
)

class WeatherDetailScreenModel(
    private val getCurrentWeatherDataUseCase: GetCurrentWeatherDataUseCase,
) : StatefulScreenModel<WeatherDetailScreenState>(initial = WeatherDetailScreenState()) {

    fun getCurrentWeatherForecast() {
        if (state.value.weatherData !is LoadState.Success)
        screenModelScope.launch {
            updateState { copy(weatherData = LoadState.Loading) }
            when (val result = getCurrentWeatherDataUseCase()) {
                is DataResult.Error -> updateState { copy(weatherData = LoadState.Error(message = result.message)) }
                is DataResult.Success -> updateState {
                    copy(
                        forecasts = result.data,
                        weatherData = LoadState.Success(data = result.data.first())
                    )
                }
            }
        }
    }

    fun onClickShowMore() {
        screenModelScope.launch {
            updateState { copy(navigateToShowMore = true) }
        }
    }

    fun resetNavigation() {
        updateState {
            copy(navigateToShowMore = false)
        }
    }

}
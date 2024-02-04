package ui.screens.list

import cafe.adriel.voyager.core.model.screenModelScope
import data.extensions.today
import data.helpers.DataResult
import data.models.WeatherForecastData
import domain.usecases.GetHistoryWeatherDataUseCase
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import ui.helpers.LoadState
import ui.helpers.StatefulScreenModel

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 22:47
 * USER    : mambo
 */

data class WeatherListScreenState(
    val historyState: LoadState<List<WeatherForecastData>> = LoadState.Loading,
    val futureState: LoadState<List<WeatherForecastData>> = LoadState.Loading
)

class WeatherListScreenModel(
    private val getHistoryWeatherDataUseCase: GetHistoryWeatherDataUseCase
) : StatefulScreenModel<WeatherListScreenState>(initial = WeatherListScreenState()) {

    fun getHistoryWeatherForecast() {
        screenModelScope.launch {
            updateState { copy(historyState = LoadState.Loading) }
            when (
                val result = getHistoryWeatherDataUseCase(
                    startDate = today,
                    endDate = today.minus(period = DatePeriod(days = 14))
                )
            ) {
                is DataResult.Error -> updateState { copy(historyState = LoadState.Error(message = result.message)) }
                is DataResult.Success -> {
                    updateState { copy(historyState = LoadState.Success(data = result.data)) }
                }
            }
        }
    }

    fun updateFutureList(list: List<WeatherForecastData>) {
        updateState {
            copy(futureState = LoadState.Success(data = list))
        }
    }

}
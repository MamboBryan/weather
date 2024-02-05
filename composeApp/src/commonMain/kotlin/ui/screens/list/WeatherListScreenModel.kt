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
    val todayState: LoadState<List<WeatherForecastData>> = LoadState.Loading,
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
                    startDate = today.minus(period = DatePeriod(days = 14)),
                    endDate = today
                )
            ) {
                is DataResult.Error -> updateState { copy(historyState = LoadState.Error(message = result.message)) }
                is DataResult.Success -> {
                    val list = result.data.toMutableList()
                    list.sortBy { it.date }
                    list.removeLast()
                    list.reverse()
                    updateState { copy(historyState = LoadState.Success(data = list)) }
                }
            }
        }
    }

    fun updateFutureList(list: List<WeatherForecastData>) {
        val futureDates = list.toMutableList()
        futureDates.removeFirst()
        updateState {
            copy(
                todayState = LoadState.Success(data = listOf(list.first())),
                futureState = LoadState.Success(data = futureDates),
            )
        }
    }

}
package ui.screens.di

import org.koin.dsl.module
import ui.screens.detail.WeatherDetailScreenModel
import ui.screens.list.WeatherListScreenModel

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 22:49
 * USER    : mambo
 */

val screenModelModule = module {
    factory { WeatherDetailScreenModel(getCurrentWeatherDataUseCase = get()) }
    factory { WeatherListScreenModel(getHistoryWeatherDataUseCase = get()) }
}
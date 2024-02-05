package domain.di

import domain.usecases.GetCurrentWeatherDataUseCase
import domain.usecases.GetHistoryWeatherDataUseCase
import org.koin.dsl.module

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 00:22
 * USER    : mambo
 */

val useCaseModule = module {
    factory { GetCurrentWeatherDataUseCase(weatherRepository = get()) }
    factory { GetHistoryWeatherDataUseCase(weatherRepository = get()) }
}
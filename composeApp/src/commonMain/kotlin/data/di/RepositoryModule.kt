package data.di

import data.repositories.WeatherRepository
import data.repositories.WeatherRepositoryImpl
import org.koin.dsl.module

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 00:21
 * USER    : mambo
 */

val repositoryModule = module {
    factory<WeatherRepository> { WeatherRepositoryImpl(remoteSource = get()) }
}
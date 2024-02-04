package sources.remotesources.di

import data.repositories.WeatherRepository
import data.repositories.WeatherRepositoryImpl
import io.ktor.client.HttpClient
import org.koin.dsl.module
import sources.remotesources.api.WeatherRemoteSource
import sources.remotesources.api.WeatherRemoteSourceImpl
import sources.remotesources.client.getHttpClient

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 00:00
 * USER    : mambo
 */

val remoteModule = module {
    single<HttpClient> { getHttpClient() }
    single<WeatherRemoteSource> { WeatherRemoteSourceImpl(client = get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(remoteSource = get()) }
}
package app

import data.di.repositoryModule
import domain.di.useCaseModule
import org.koin.core.context.startKoin
import sources.remotesources.di.remoteModule
import ui.screens.di.screenModelModule

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 00:34
 * USER    : mambo
 */

fun initKoin() {
    startKoin {
        modules(remoteModule, repositoryModule, useCaseModule, screenModelModule)
    }
}
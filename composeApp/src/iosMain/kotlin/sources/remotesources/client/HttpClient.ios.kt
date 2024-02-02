package sources.remotesources.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 21:57
 * USER    : mambo
 */
actual fun getHttpClient(): HttpClient = HttpClient(Darwin)
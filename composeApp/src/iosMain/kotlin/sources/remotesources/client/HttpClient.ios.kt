package sources.remotesources.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 21:57
 * USER    : mambo
 */

actual fun getHttpClient(): HttpClient = HttpClient(Darwin){

    install(ContentNegotiation) {
        json(json = Json {
            ignoreUnknownKeys = true
        })
    }

    install(Logging) {
        level = LogLevel.BODY
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    install(DefaultRequest) {
        header(HttpHeaders.Accept, ContentType.Application.Json)
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

}
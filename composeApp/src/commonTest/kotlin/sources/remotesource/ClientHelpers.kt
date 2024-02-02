package sources.remotesource

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 22:13
 * USER    : mambo
 */

/**
 * Generates a fake [HttpClient] to use for testing
 * @param engine is the engine to use to testing, in any test case should be [MockEngine]
 * @return [HttpClient]
 */
fun generateFakeClient(engine: HttpClientEngine) = HttpClient(engine) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}

/**
 * Generates a mock [HttpClientEngine] to use for testing
 * @param statusCode the status code the response should return
 * @param response the response body as string that the request should return
 * @return [MockEngine]
 */
fun generateFakeEngine(statusCode: HttpStatusCode, response: String) = MockEngine {
    val headers = headers {
        append(HttpHeaders.ContentType, "application/json")
    }
    respond(content = response, status = statusCode, headers = headers)
}
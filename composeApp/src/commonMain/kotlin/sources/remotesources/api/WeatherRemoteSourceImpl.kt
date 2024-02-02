package sources.remotesources.api

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.url
import sources.remotesources.dtos.WeatherCurrentAndDaysForecastDTO
import sources.remotesources.helpers.Endpoint
import sources.remotesources.helpers.NetworkResult
import sources.remotesources.helpers.safeApiCall

/**
 * PROJECT : weather
 * DATE    : Fri 02/02/2024
 * TIME    : 20:38
 * USER    : mambo
 */

class WeatherRemoteSourceImpl(private val client: HttpClient) : WeatherRemoteSource {

    private val key = "ADD KEY HERE"

    data class Query(val name: String, val value: String)

    /**
     * Helper extension function for [HttpRequestBuilder] to aid in building the url with
     */
    private fun HttpRequestBuilder.addUrl(endpoint: Endpoint, queries: List<Query>) {
        url(
            buildString {
                append(endpoint.url)
                append("?")
                queries.forEach { append("${it.name}=${it.value}&") }
            }
        )
    }

    private fun Boolean.toQueryParameter() = when (this) {
        true -> "yes"
        false -> "no"
    }

    override suspend fun fetchCurrentAndFutureWeatherForecast(
        city: String,
        days: Int,
        isAirQualityEnabled: Boolean,
        isAlertsEnabled: Boolean
    ): NetworkResult<WeatherCurrentAndDaysForecastDTO> = safeApiCall(error = "") {
        client.get {
            addUrl(
                endpoint = Endpoint.Forecast,
                queries = listOf(
                    Query(name = "key", value = key),
                    Query(name = "city", value = city),
                    Query(name = "aqi", value = isAirQualityEnabled.toQueryParameter()),
                    Query(name = "alerts", value = isAlertsEnabled.toQueryParameter()),
                )
            )
        }
    }

}
package sources.remotesources.api

import data.extensions.asYYYYMMDD
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.datetime.LocalDate
import sources.remotesources.dtos.WeatherCurrentAndDaysForecastDTO
import sources.remotesources.dtos.WeatherHistoryDTO
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

    private val key = "4547cb951914443bae4104513240102"

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

    /**
     * Changes boolean value to valid request query parameter
     */
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
                    Query(name = "q", value = city),
                    Query(name = "days", value = "$days"),
                    Query(name = "aqi", value = isAirQualityEnabled.toQueryParameter()),
                    Query(name = "alerts", value = isAlertsEnabled.toQueryParameter()),
                )
            )
        }
    }

    override suspend fun fetchPastWeatherDates(
        city: String,
        startDate: LocalDate,
        endDate: LocalDate,
        isAirQualityEnabled: Boolean,
        isAlertsEnabled: Boolean
    ): NetworkResult<WeatherHistoryDTO> = safeApiCall(error = "Unable to get past weather data ") {
        client.get {
            addUrl(
                endpoint = Endpoint.History,
                queries = listOf(
                    Query(name = "key", value = key),
                    Query(name = "q", value = city),
                    Query(name = "aqi", value = isAirQualityEnabled.toQueryParameter()),
                    Query(name = "alerts", value = isAlertsEnabled.toQueryParameter()),
                    Query(name = "dt", value = startDate.asYYYYMMDD()),
                    Query(name = "end_dt", value = endDate.asYYYYMMDD())
                )
            )
        }
    }

}
package data.helpers

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 00:17
 * USER    : mambo
 */

sealed interface DataResult<out T> {

    data class Error(val message: String) : DataResult<Nothing>

    data class Success<T>(val data: T) : DataResult<T>

}
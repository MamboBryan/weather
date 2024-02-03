package ui.helpers

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 18:59
 * USER    : mambo
 */

/**
 * An interface class
 */
sealed interface LoadState<out T> {

    data object Loading : LoadState<Nothing>

    data class Error(val message: String) : LoadState<Nothing>

    data class Success<T>(val data: T) : LoadState<T>

}
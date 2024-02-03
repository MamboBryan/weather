package data.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 01:45
 * USER    : mambo
 */

fun String.asLocalDate(): LocalDate = this.toLocalDate()

fun String.asLocalDateTime(): LocalDateTime {
    return this.replace(" ", "T").plus(":00")
        .toLocalDateTime()
}
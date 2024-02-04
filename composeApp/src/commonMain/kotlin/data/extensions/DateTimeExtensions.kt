package data.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 02:59
 * USER    : mambo
 */

fun LocalDateTime.as24HourTime() = this.toString().takeLast(5)

fun LocalDate.asYYYYMMDD() = "$year-${month.number}-$dayOfMonth"
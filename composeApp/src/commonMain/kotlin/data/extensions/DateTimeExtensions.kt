package data.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 02:59
 * USER    : mambo
 */

val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date

fun LocalDateTime.as24HourTime() = this.toString().takeLast(5)

fun LocalDate.asYYYYMMDD() = "$year-${month.number}-$dayOfMonth"
package data.extensions

import kotlinx.datetime.LocalDateTime

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 02:59
 * USER    : mambo
 */

fun LocalDateTime.as24HourTime() = this.toString().takeLast(5)
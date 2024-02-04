package data.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 03:01
 * USER    : mambo
 */

class DateTimeExtensionsTest {

    @Test
    fun `given a valid LocalDateTime - should return 24 hour time only`() {
        listOf(
            Triple(21, 23, "21:23"),
            Triple(0, 42, "00:42"),
            Triple(18, 5, "18:05"),
            Triple(1, 12, "01:12"),
            Triple(15, 15, "15:15"),
            Triple(2, 23, "02:23"),
        ).map {
            val time = LocalDateTime(2024, 2, 3, it.first, it.second)
            assertEquals(expected = it.third, actual = time.as24HourTime())
        }
    }

    @Test
    fun `given a valid LocalDate - should return value as YYYYMMDD`() {
        val date = LocalDate(2024, 1, 1)
        assertEquals(expected = "2024-1-1", actual = date.asYYYYMMDD())
    }

}
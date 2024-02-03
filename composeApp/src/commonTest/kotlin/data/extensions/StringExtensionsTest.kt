package data.extensions

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 01:56
 * USER    : mambo
 */

class StringExtensionsTest {

    @Test
    fun `given 2024-02-02 as a String asLocalDate converts it to LocalDate`() {
        assertNotNull("2024-02-02".asLocalDate())
    }

    @Test
    fun `given 2024-01-25 as a String converts to a valid LocalDate`() {
        assertEquals("2024-01-25".asLocalDate(), LocalDate(2024, 1, 25))
    }

    @Test
    fun `given 2024-13-02 fails to convert to LocalDate and fails`() {
        assertFails { "2024-13-02".asLocalDate() }
    }

    @Test
    fun `given a valid time as a String asLocalDateTime converts it to LocalDateTime`() {
        assertNotNull("2024-01-25 02:00".asLocalDateTime())
    }

    @Test
    fun `given a valid time as a String asLocalDateTime converts it to a valid LocalDateTime`() {
        assertEquals(
            expected = "2024-01-25 02:00".asLocalDateTime(),
            actual = LocalDateTime(2024, 1, 25, 2, 0, 0)
        )
    }

    @Test
    fun `given an invalid time pattern as a String asLocalDateTime throws an error`() {
        assertFails {
            "2024-01-25 26:00".asLocalDateTime()
        }
    }

}
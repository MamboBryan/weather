package data.extensions

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 01:38
 * USER    : mambo
 */

class IntExtensionsTest {

    @Test
    fun `given 0 when converted with asBoolean should return false`() {
        assertFalse { 0.asBoolean() }
    }

    @Test
    fun `given 1 when converted with asBoolean should return true`() {
        assertTrue { 1.asBoolean() }
    }

    @Test
    fun `given any other number apart from 1 when converted with asBoolean should return false`() {
        repeat(20) {
            assertFalse { Random.nextInt(from = 2, until = Int.MAX_VALUE).asBoolean() }
        }
    }

}
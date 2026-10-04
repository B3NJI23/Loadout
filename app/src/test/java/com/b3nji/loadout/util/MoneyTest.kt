package com.b3nji.loadout.util

import org.junit.Assert.assertEquals
import org.junit.Test

class MoneyTest {

    @Test
    fun `groups thousands with non-breaking spaces`() {
        assertEquals("1 234 567 Ft", formatFt(1_234_567))
        assertEquals("999 Ft", formatFt(999))
        assertEquals("0 Ft", formatFt(0))
    }

    @Test
    fun `negative amounts get a minus sign`() {
        assertEquals("−12 000 Ft", formatFt(-12_000))
    }

    @Test
    fun `digitsOnly strips everything that is not a digit`() {
        assertEquals("650000", digitsOnly("650 000 Ft"))
        assertEquals("123456789", digitsOnly("1234567890"))
    }
}

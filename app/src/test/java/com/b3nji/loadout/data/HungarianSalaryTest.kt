package com.b3nji.loadout.data

import org.junit.Assert.assertEquals
import org.junit.Test

class HungarianSalaryTest {

    @Test
    fun `standard deductions take 33_5 percent`() {
        val result = HungarianSalary.calculate(500_000)
        assertEquals(92_500, result.socialSecurity)
        assertEquals(75_000, result.personalIncomeTax)
        assertEquals(332_500, result.net)
    }

    @Test
    fun `under 25 pays no income tax below the cap`() {
        val result = HungarianSalary.calculate(500_000, SalaryOptions(under25 = true))
        assertEquals(0, result.personalIncomeTax)
        assertEquals(407_500, result.net)
    }

    @Test
    fun `under 25 pays income tax only on the part above the cap`() {
        val result = HungarianSalary.calculate(800_000, SalaryOptions(under25 = true))
        // (800 000 - 686 374) * 15% = 17 043.9 -> 17 044
        assertEquals(17_044, result.personalIncomeTax)
        assertEquals(148_000, result.socialSecurity)
        assertEquals(634_956, result.net)
    }

    @Test
    fun `newlywed credit reduces income tax by 5000`() {
        val result = HungarianSalary.calculate(500_000, SalaryOptions(newlywed = true))
        assertEquals(70_000, result.personalIncomeTax)
        assertEquals(337_500, result.net)
    }

    @Test
    fun `income tax never goes negative`() {
        val result = HungarianSalary.calculate(500_000, SalaryOptions(under25 = true, newlywed = true))
        assertEquals(0, result.personalIncomeTax)
    }

    @Test
    fun `zero or negative gross gives zero everywhere`() {
        assertEquals(SalaryBreakdown(0, 0, 0, 0), HungarianSalary.calculate(0))
        assertEquals(SalaryBreakdown(0, 0, 0, 0), HungarianSalary.calculate(-10))
    }
}

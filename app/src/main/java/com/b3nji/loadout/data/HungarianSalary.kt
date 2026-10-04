package com.b3nji.loadout.data

import kotlin.math.max
import kotlin.math.roundToLong

/** Which optional Hungarian tax allowances apply to the user. */
data class SalaryOptions(
    val under25: Boolean = false,
    val newlywed: Boolean = false,
)

/** Monthly amounts in forints. */
data class SalaryBreakdown(
    val gross: Long,
    val socialSecurity: Long,
    val personalIncomeTax: Long,
    val net: Long,
)

/**
 * Bruttó -> nettó for a regular Hungarian employee.
 *
 * Employee-side deductions:
 *  - TB járulék (social security): 18.5% of gross
 *  - SZJA (personal income tax): 15% of gross, reduced by allowances
 */
object HungarianSalary {
    const val SOCIAL_SECURITY_RATE = 0.185
    const val PERSONAL_INCOME_TAX_RATE = 0.15

    /**
     * Under-25s pay no SZJA on income up to this monthly amount (the national average gross wage).
     * This is the 2025 figure; it is updated every year, so check it when the year changes.
     */
    const val UNDER_25_MONTHLY_CAP = 686_374L

    /** Friss házasok kedvezménye: 5 000 Ft less SZJA per month, for up to 24 months. */
    const val NEWLYWED_TAX_CREDIT = 5_000L

    fun calculate(gross: Long, options: SalaryOptions = SalaryOptions()): SalaryBreakdown {
        if (gross <= 0) return SalaryBreakdown(0, 0, 0, 0)

        val socialSecurity = (gross * SOCIAL_SECURITY_RATE).roundToLong()

        val taxableIncome = if (options.under25) max(0L, gross - UNDER_25_MONTHLY_CAP) else gross
        var incomeTax = (taxableIncome * PERSONAL_INCOME_TAX_RATE).roundToLong()
        if (options.newlywed) incomeTax = max(0L, incomeTax - NEWLYWED_TAX_CREDIT)

        return SalaryBreakdown(
            gross = gross,
            socialSecurity = socialSecurity,
            personalIncomeTax = incomeTax,
            net = gross - socialSecurity - incomeTax,
        )
    }
}

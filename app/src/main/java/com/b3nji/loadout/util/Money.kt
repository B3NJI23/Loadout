package com.b3nji.loadout.util

import kotlin.math.abs

private const val NBSP = ' '

/** 1234567 -> "1 234 567 Ft", Hungarian style with non-breaking spaces. */
fun formatFt(amount: Long): String {
    val grouped = abs(amount).toString().reversed().chunked(3).joinToString(NBSP.toString()).reversed()
    val sign = if (amount < 0) "−" else ""
    return "$sign$grouped${NBSP}Ft"
}

/** Keeps only digits so a text field can't hold anything that isn't a forint amount. */
fun digitsOnly(text: String, maxLength: Int = 9): String = text.filter(Char::isDigit).take(maxLength)

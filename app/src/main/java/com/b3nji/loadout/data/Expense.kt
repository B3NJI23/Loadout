package com.b3nji.loadout.data

/** One budget line the user can put money into. [amount] is monthly, in forints. */
data class Expense(
    val id: Long,
    val name: String,
    val hint: String = "",
    val amount: Long = 0,
)

val defaultExpenses = listOf(
    Expense(1, "Rent", "albérlet / lakbér"),
    Expense(2, "Common charges", "közös költség"),
    Expense(3, "Electricity", "villany"),
    Expense(4, "Gas / heating", "gáz / fűtés"),
    Expense(5, "Water", "víz"),
    Expense(6, "Internet & phone"),
    Expense(7, "Groceries", "élelmiszer"),
    Expense(8, "Car / gas / transport", "benzin, bérlet"),
    Expense(9, "Fun", "szórakozás"),
    Expense(10, "Savings", "megtakarítás"),
)

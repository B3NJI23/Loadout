package com.b3nji.loadout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.b3nji.loadout.data.Expense
import com.b3nji.loadout.data.HungarianSalary
import com.b3nji.loadout.data.SalaryBreakdown
import com.b3nji.loadout.data.SalaryOptions
import com.b3nji.loadout.data.defaultExpenses
import com.b3nji.loadout.util.digitsOnly

/**
 * Holds the app's state and survives screen rotation.
 * Anything read from a `mutableStateOf` / `mutableStateListOf` automatically
 * redraws the screens that use it when it changes.
 */
class LoadoutViewModel : ViewModel() {
    var grossInput by mutableStateOf("")
        private set

    var options by mutableStateOf(SalaryOptions())
        private set

    val expenses = mutableStateListOf<Expense>().apply { addAll(defaultExpenses) }

    private var nextExpenseId = (defaultExpenses.maxOf { it.id }) + 1

    val salary: SalaryBreakdown
        get() = HungarianSalary.calculate(grossInput.toLongOrNull() ?: 0, options)

    val allocated: Long
        get() = expenses.sumOf { it.amount }

    val remaining: Long
        get() = salary.net - allocated

    fun onGrossChange(text: String) {
        grossInput = digitsOnly(text)
    }

    fun setUnder25(enabled: Boolean) {
        options = options.copy(under25 = enabled)
    }

    fun setNewlywed(enabled: Boolean) {
        options = options.copy(newlywed = enabled)
    }

    fun onExpenseAmountChange(id: Long, text: String) {
        val index = expenses.indexOfFirst { it.id == id }
        if (index == -1) return
        expenses[index] = expenses[index].copy(amount = digitsOnly(text).toLongOrNull() ?: 0)
    }

    fun addExpense(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        expenses.add(Expense(id = nextExpenseId++, name = trimmed))
    }

    fun removeExpense(id: Long) {
        expenses.removeAll { it.id == id }
    }
}

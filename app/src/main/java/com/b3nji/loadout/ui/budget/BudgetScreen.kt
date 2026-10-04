package com.b3nji.loadout.ui.budget

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.b3nji.loadout.LoadoutViewModel
import com.b3nji.loadout.data.Expense
import com.b3nji.loadout.ui.components.LoadoutCard
import com.b3nji.loadout.util.formatFt

@Composable
fun BudgetScreen(
    viewModel: LoadoutViewModel,
    onEditIncome: () -> Unit,
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .padding(horizontal = 16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Budget", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                TextButton(onClick = onEditIncome) { Text("Edit income") }
            }
            Spacer(Modifier.height(8.dp))

            // Pinned at the top so it stays visible while scrolling the list.
            SpendableCard(net = viewModel.salary.net, allocated = viewModel.allocated, remaining = viewModel.remaining)

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(viewModel.expenses, key = { it.id }) { expense ->
                    ExpenseRow(
                        expense = expense,
                        onAmountChange = { viewModel.onExpenseAmountChange(expense.id, it) },
                        onRemove = { viewModel.removeExpense(expense.id) },
                    )
                }
                item { AddExpenseRow(onAdd = viewModel::addExpense) }
            }
        }
    }
}

@Composable
private fun SpendableCard(net: Long, allocated: Long, remaining: Long) {
    val fraction = if (net > 0) (remaining.toFloat() / net).coerceIn(0f, 1f) else 0f
    val animatedFraction by animateFloatAsState(fraction, label = "spendable")
    val overBudget = remaining < 0
    val barColor by animateColorAsState(
        if (overBudget) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
        label = "barColor",
    )

    LoadoutCard {
        Text("Left to spend", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            formatFt(remaining),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = barColor,
        )
        Spacer(Modifier.height(12.dp))
        LinearProgressIndicator(
            progress = { animatedFraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp),
            color = barColor,
            trackColor = MaterialTheme.colorScheme.outline,
            strokeCap = StrokeCap.Round,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            if (overBudget) "Over budget by ${formatFt(-remaining)}" else "${formatFt(allocated)} of ${formatFt(net)} allocated",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun ExpenseRow(
    expense: Expense,
    onAmountChange: (String) -> Unit,
    onRemove: () -> Unit,
) {
    LoadoutCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(expense.name, style = MaterialTheme.typography.bodyLarge)
                if (expense.hint.isNotEmpty()) {
                    Text(expense.hint, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                TextButton(onClick = onRemove, contentPadding = PaddingValues(0.dp)) {
                    Text("Remove", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.width(12.dp))
            OutlinedTextField(
                value = if (expense.amount == 0L) "" else expense.amount.toString(),
                onValueChange = onAmountChange,
                modifier = Modifier.width(150.dp),
                placeholder = { Text("0") },
                suffix = { Text("Ft") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = MaterialTheme.shapes.small,
            )
        }
    }
}

@Composable
private fun AddExpenseRow(onAdd: (String) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }

    LoadoutCard {
        Text("Add your own", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("e.g. Gym") },
                singleLine = true,
                shape = MaterialTheme.shapes.small,
            )
            Spacer(Modifier.width(12.dp))
            Button(
                onClick = {
                    onAdd(name)
                    name = ""
                },
                enabled = name.isNotBlank(),
                shape = MaterialTheme.shapes.small,
            ) { Text("Add") }
        }
    }
}

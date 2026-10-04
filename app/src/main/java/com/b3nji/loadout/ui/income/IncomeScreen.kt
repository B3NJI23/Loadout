package com.b3nji.loadout.ui.income

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.b3nji.loadout.LoadoutViewModel
import com.b3nji.loadout.ui.components.LoadoutCard
import com.b3nji.loadout.util.formatFt

@Composable
fun IncomeScreen(
    viewModel: LoadoutViewModel,
    onContinue: () -> Unit,
) {
    val salary = viewModel.salary

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Loadout", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Text(
                "Planning a move? Start with your monthly salary.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            LoadoutCard {
                Text("Monthly gross salary (bruttó)", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = viewModel.grossInput,
                    onValueChange = viewModel::onGrossChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g. 650000") },
                    suffix = { Text("Ft") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = MaterialTheme.shapes.small,
                )
            }

            LoadoutCard {
                ToggleRow(
                    title = "I'm under 25",
                    subtitle = "No SZJA up to the yearly cap",
                    checked = viewModel.options.under25,
                    onCheckedChange = viewModel::setUnder25,
                )
                HorizontalDivider(Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outline)
                ToggleRow(
                    title = "Newlywed (friss házas)",
                    subtitle = "5 000 Ft less SZJA a month",
                    checked = viewModel.options.newlywed,
                    onCheckedChange = viewModel::setNewlywed,
                )
            }

            LoadoutCard {
                BreakdownRow("Bruttó", formatFt(salary.gross))
                BreakdownRow("TB járulék (18.5%)", "−" + formatFt(salary.socialSecurity))
                BreakdownRow("SZJA (15%)", "−" + formatFt(salary.personalIncomeTax))
                HorizontalDivider(Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outline)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Nettó", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    Text(
                        formatFt(salary.net),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            Button(
                onClick = onContinue,
                enabled = salary.net > 0,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = MaterialTheme.shapes.small,
            ) {
                Text("Plan my budget →", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun ToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.width(12.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun BreakdownRow(label: String, value: String) {
    Row(Modifier.padding(vertical = 4.dp)) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        Text(value)
    }
}

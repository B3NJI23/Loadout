package com.b3nji.loadout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3nji.loadout.ui.budget.BudgetScreen
import com.b3nji.loadout.ui.income.IncomeScreen
import com.b3nji.loadout.ui.theme.LoadoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoadoutTheme {
                LoadoutApp()
            }
        }
    }
}

private enum class Screen { Income, Budget }

@Composable
private fun LoadoutApp(viewModel: LoadoutViewModel = viewModel()) {
    var screen by rememberSaveable { mutableStateOf(Screen.Income) }

    when (screen) {
        Screen.Income -> IncomeScreen(
            viewModel = viewModel,
            onContinue = { screen = Screen.Budget },
        )

        Screen.Budget -> {
            // The phone's back gesture returns to the income page instead of closing the app.
            BackHandler { screen = Screen.Income }
            BudgetScreen(
                viewModel = viewModel,
                onEditIncome = { screen = Screen.Income },
            )
        }
    }
}

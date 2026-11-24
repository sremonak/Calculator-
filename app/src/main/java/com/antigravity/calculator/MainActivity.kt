package com.antigravity.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.antigravity.calculator.ui.CalculatorScreen
import com.antigravity.calculator.ui.CalculatorViewModel
import com.antigravity.calculator.ui.theme.HumanCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HumanCalculatorTheme {
                val viewModel: CalculatorViewModel = viewModel()
                CalculatorScreen(viewModel = viewModel)
            }
        }
    }
}

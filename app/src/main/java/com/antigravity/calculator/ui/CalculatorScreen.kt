package com.antigravity.calculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.antigravity.calculator.model.CalculatorAction
import com.antigravity.calculator.model.CalculatorOperation
import com.antigravity.calculator.ui.components.CalculatorButton
import com.antigravity.calculator.ui.components.DisplaySection
import com.antigravity.calculator.ui.components.HistoryBottomSheet
import com.antigravity.calculator.ui.theme.DarkBackground
import com.antigravity.calculator.ui.theme.KeyEqualsBg
import com.antigravity.calculator.ui.theme.KeyEqualsText
import com.antigravity.calculator.ui.theme.KeyFunctionBg
import com.antigravity.calculator.ui.theme.KeyFunctionText
import com.antigravity.calculator.ui.theme.KeyNumberBg
import com.antigravity.calculator.ui.theme.KeyNumberText
import com.antigravity.calculator.ui.theme.KeyOperatorBg
import com.antigravity.calculator.ui.theme.KeyOperatorText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .safeDrawingPadding()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Display section occupying top area
        DisplaySection(
            expression = state.expressionPreview,
            result = state.displayResult,
            errorMessage = state.errorMessage,
            onDelete = { viewModel.onAction(CalculatorAction.Delete) },
            onToggleHistory = { viewModel.onAction(CalculatorAction.ToggleHistory) },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Keypad grid occupying comfortable thumb reach
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Row 1: Function Keys
            Row(modifier = Modifier.fillMaxWidth()) {
                val clearLabel = if (state.number1.isNotBlank() || state.number2.isNotBlank()) "C" else "AC"
                val clearSpoken = if (clearLabel == "C") "Clear entry" else "All clear"
                CalculatorButton(
                    symbol = clearLabel,
                    spokenLabel = clearSpoken,
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyFunctionBg,
                    textColor = KeyFunctionText,
                    onClick = {
                        if (clearLabel == "C") viewModel.onAction(CalculatorAction.Clear)
                        else viewModel.onAction(CalculatorAction.AllClear)
                    }
                )
                CalculatorButton(
                    symbol = "+/−",
                    spokenLabel = "Toggle positive or negative sign",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyFunctionBg,
                    textColor = KeyFunctionText,
                    onClick = { viewModel.onAction(CalculatorAction.ToggleSign) }
                )
                CalculatorButton(
                    symbol = "%",
                    spokenLabel = "Percent",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyFunctionBg,
                    textColor = KeyFunctionText,
                    onClick = { viewModel.onAction(CalculatorAction.Percentage) }
                )
                CalculatorButton(
                    symbol = "÷",
                    spokenLabel = "Divided by",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyOperatorBg,
                    textColor = KeyOperatorText,
                    onClick = { viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide)) }
                )
            }

            // Row 2: 7, 8, 9, ×
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorButton(
                    symbol = "7",
                    spokenLabel = "7",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(7)) }
                )
                CalculatorButton(
                    symbol = "8",
                    spokenLabel = "8",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(8)) }
                )
                CalculatorButton(
                    symbol = "9",
                    spokenLabel = "9",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(9)) }
                )
                CalculatorButton(
                    symbol = "×",
                    spokenLabel = "Multiplied by",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyOperatorBg,
                    textColor = KeyOperatorText,
                    onClick = { viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply)) }
                )
            }

            // Row 3: 4, 5, 6, −
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorButton(
                    symbol = "4",
                    spokenLabel = "4",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(4)) }
                )
                CalculatorButton(
                    symbol = "5",
                    spokenLabel = "5",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(5)) }
                )
                CalculatorButton(
                    symbol = "6",
                    spokenLabel = "6",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(6)) }
                )
                CalculatorButton(
                    symbol = "−",
                    spokenLabel = "Minus",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyOperatorBg,
                    textColor = KeyOperatorText,
                    onClick = { viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Subtract)) }
                )
            }

            // Row 4: 1, 2, 3, +
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorButton(
                    symbol = "1",
                    spokenLabel = "1",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(1)) }
                )
                CalculatorButton(
                    symbol = "2",
                    spokenLabel = "2",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(2)) }
                )
                CalculatorButton(
                    symbol = "3",
                    spokenLabel = "3",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(3)) }
                )
                CalculatorButton(
                    symbol = "+",
                    spokenLabel = "Plus",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyOperatorBg,
                    textColor = KeyOperatorText,
                    onClick = { viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add)) }
                )
            }

            // Row 5: 0, ., =
            Row(modifier = Modifier.fillMaxWidth()) {
                CalculatorButton(
                    symbol = "0",
                    spokenLabel = "0",
                    modifier = Modifier.weight(2f),
                    isWide = true,
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Number(0)) }
                )
                CalculatorButton(
                    symbol = ".",
                    spokenLabel = "Decimal point",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyNumberBg,
                    textColor = KeyNumberText,
                    onClick = { viewModel.onAction(CalculatorAction.Decimal) }
                )
                CalculatorButton(
                    symbol = "=",
                    spokenLabel = "Equals",
                    modifier = Modifier.weight(1f),
                    backgroundColor = KeyEqualsBg,
                    textColor = KeyEqualsText,
                    onClick = { viewModel.onAction(CalculatorAction.Calculate) }
                )
            }
        }
    }

    if (state.isHistoryOpen) {
        HistoryBottomSheet(
            sheetState = sheetState,
            history = state.history,
            onSelectRecord = { record -> viewModel.onAction(CalculatorAction.UseHistory(record)) },
            onClearHistory = { viewModel.onAction(CalculatorAction.AllClear) },
            onDismiss = { viewModel.onAction(CalculatorAction.ToggleHistory) }
        )
    }
}

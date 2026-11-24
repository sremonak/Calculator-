package com.antigravity.calculator.ui

import androidx.lifecycle.ViewModel
import com.antigravity.calculator.engine.CalculatorEngine
import com.antigravity.calculator.model.CalculationRecord
import com.antigravity.calculator.model.CalculatorAction
import com.antigravity.calculator.model.CalculatorOperation
import com.antigravity.calculator.model.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

class CalculatorViewModel : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDelete()
            is CalculatorAction.Clear -> clearCurrent()
            is CalculatorAction.AllClear -> clearAll()
            is CalculatorAction.Percentage -> performPercentage()
            is CalculatorAction.ToggleSign -> toggleSign()
            is CalculatorAction.ToggleHistory -> toggleHistory()
            is CalculatorAction.UseHistory -> applyHistoryRecord(action.item)
        }
    }

    private fun enterNumber(number: Int) {
        _state.update { currentState ->
            if (currentState.operation == null) {
                val newNum1 = if (currentState.number1 == "0") number.toString() else currentState.number1 + number
                currentState.copy(
                    number1 = newNum1,
                    displayResult = newNum1,
                    expressionPreview = "",
                    errorMessage = null
                )
            } else {
                val newNum2 = if (currentState.number2 == "0") number.toString() else currentState.number2 + number
                currentState.copy(
                    number2 = newNum2,
                    displayResult = newNum2,
                    expressionPreview = "${currentState.number1} ${currentState.operation.symbol}",
                    errorMessage = null
                )
            }
        }
    }

    private fun enterDecimal() {
        _state.update { currentState ->
            if (currentState.operation == null) {
                if (!currentState.number1.contains(".")) {
                    val newNum = if (currentState.number1.isBlank()) "0." else "${currentState.number1}."
                    currentState.copy(number1 = newNum, displayResult = newNum, errorMessage = null)
                } else currentState
            } else {
                if (!currentState.number2.contains(".")) {
                    val newNum = if (currentState.number2.isBlank()) "0." else "${currentState.number2}."
                    currentState.copy(number2 = newNum, displayResult = newNum, errorMessage = null)
                } else currentState
            }
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        val current = _state.value
        if (current.number1.isNotBlank()) {
            if (current.number2.isNotBlank()) {
                // Chain calculation: calculate previous pair first
                performCalculation()
            }
            _state.update {
                it.copy(
                    operation = operation,
                    expressionPreview = "${it.number1} ${operation.symbol}",
                    errorMessage = null
                )
            }
        } else if (current.displayResult.isNotBlank() && current.displayResult != "0") {
            _state.update {
                it.copy(
                    number1 = it.displayResult,
                    operation = operation,
                    expressionPreview = "${it.displayResult} ${operation.symbol}",
                    errorMessage = null
                )
            }
        }
    }

    private fun performCalculation() {
        val current = _state.value
        val num1 = current.number1
        val num2 = current.number2
        val op = current.operation

        if (num1.isNotBlank() && num2.isNotBlank() && op != null) {
            val calcResult = CalculatorEngine.calculate(num1, num2, op)
            calcResult.onSuccess { res ->
                val formatted = CalculatorEngine.formatDisplay(res)
                val expression = "$num1 ${op.symbol} $num2"
                val record = CalculationRecord(expression = expression, result = formatted)
                _state.update {
                    it.copy(
                        number1 = formatted,
                        number2 = "",
                        operation = null,
                        displayResult = formatted,
                        expressionPreview = expression,
                        errorMessage = null,
                        history = listOf(record) + it.history
                    )
                }
            }.onFailure { err ->
                _state.update {
                    it.copy(errorMessage = err.message ?: "Calculation error")
                }
            }
        }
    }

    private fun performPercentage() {
        val current = _state.value
        val target = if (current.operation != null && current.number2.isNotBlank()) current.number2 else current.number1
        if (target.isNotBlank()) {
            CalculatorEngine.applyPercentage(target).onSuccess { res ->
                val formatted = CalculatorEngine.formatDisplay(res)
                _state.update {
                    if (it.operation != null && it.number2.isNotBlank()) {
                        it.copy(number2 = formatted, displayResult = formatted)
                    } else {
                        it.copy(number1 = formatted, displayResult = formatted)
                    }
                }
            }
        }
    }

    private fun toggleSign() {
        _state.update { current ->
            if (current.operation == null && current.number1.isNotBlank()) {
                val toggled = if (current.number1.startsWith("-")) current.number1.substring(1) else "-${current.number1}"
                current.copy(number1 = toggled, displayResult = toggled)
            } else if (current.number2.isNotBlank()) {
                val toggled = if (current.number2.startsWith("-")) current.number2.substring(1) else "-${current.number2}"
                current.copy(number2 = toggled, displayResult = toggled)
            } else current
        }
    }

    private fun performDelete() {
        _state.update { current ->
            if (current.number2.isNotBlank()) {
                val updated = current.number2.dropLast(1)
                current.copy(number2 = updated, displayResult = updated.ifEmpty { "0" })
            } else if (current.operation != null) {
                current.copy(operation = null, expressionPreview = "")
            } else if (current.number1.isNotBlank()) {
                val updated = current.number1.dropLast(1)
                current.copy(number1 = updated, displayResult = updated.ifEmpty { "0" })
            } else {
                current.copy(displayResult = "0")
            }
        }
    }

    private fun clearCurrent() {
        _state.update { current ->
            if (current.number2.isNotBlank()) {
                current.copy(number2 = "", displayResult = "0")
            } else {
                current.copy(number1 = "", operation = null, displayResult = "0", expressionPreview = "")
            }
        }
    }

    private fun clearAll() {
        _state.update {
            it.copy(
                number1 = "",
                number2 = "",
                operation = null,
                displayResult = "0",
                expressionPreview = "",
                errorMessage = null
            )
        }
    }

    private fun toggleHistory() {
        _state.update { it.copy(isHistoryOpen = !it.isHistoryOpen) }
    }

    private fun applyHistoryRecord(record: CalculationRecord) {
        _state.update {
            it.copy(
                number1 = record.result,
                number2 = "",
                operation = null,
                displayResult = record.result,
                expressionPreview = "Recalled: ${record.expression}",
                isHistoryOpen = false
            )
        }
    }
}

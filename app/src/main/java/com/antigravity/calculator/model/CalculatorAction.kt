package com.antigravity.calculator.model

sealed interface CalculatorAction {
    data class Number(val number: Int) : CalculatorAction
    data object Clear : CalculatorAction
    data object AllClear : CalculatorAction
    data object Delete : CalculatorAction
    data object Decimal : CalculatorAction
    data object Calculate : CalculatorAction
    data class Operation(val operation: CalculatorOperation) : CalculatorAction
    data object Percentage : CalculatorAction
    data object ToggleSign : CalculatorAction
    data class UseHistory(val item: CalculationRecord) : CalculatorAction
    data object ToggleHistory : CalculatorAction
}

enum class CalculatorOperation(val symbol: String, val spokenName: String) {
    Add("+", "Plus"),
    Subtract("−", "Minus"),
    Multiply("×", "Multiplied by"),
    Divide("÷", "Divided by")
}

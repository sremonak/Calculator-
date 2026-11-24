package com.antigravity.calculator.model

data class CalculationRecord(
    val id: Long = System.currentTimeMillis(),
    val expression: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null,
    val displayResult: String = "0",
    val expressionPreview: String = "",
    val errorMessage: String? = null,
    val history: List<CalculationRecord> = emptyList(),
    val isHistoryOpen: Boolean = false
)

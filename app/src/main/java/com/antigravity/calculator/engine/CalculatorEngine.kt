package com.antigravity.calculator.engine

import com.antigravity.calculator.model.CalculatorOperation
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object CalculatorEngine {

    private val mathContext = MathContext(16, RoundingMode.HALF_UP)

    fun calculate(
        num1Str: String,
        num2Str: String,
        operation: CalculatorOperation
    ): Result<BigDecimal> {
        val num1 = num1Str.toBigDecimalOrNull() ?: return Result.failure(IllegalArgumentException("Invalid first operand"))
        val num2 = num2Str.toBigDecimalOrNull() ?: return Result.failure(IllegalArgumentException("Invalid second operand"))

        return try {
            val result = when (operation) {
                CalculatorOperation.Add -> num1.add(num2, mathContext)
                CalculatorOperation.Subtract -> num1.subtract(num2, mathContext)
                CalculatorOperation.Multiply -> num1.multiply(num2, mathContext)
                CalculatorOperation.Divide -> {
                    if (num2.compareTo(BigDecimal.ZERO) == 0) {
                        return Result.failure(ArithmeticException("Cannot divide by zero"))
                    }
                    num1.divide(num2, 10, RoundingMode.HALF_UP)
                }
            }
            val stripped = if (result.compareTo(BigDecimal.ZERO) == 0) {
                BigDecimal.ZERO
            } else {
                result.stripTrailingZeros()
            }
            Result.success(stripped)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun applyPercentage(numberStr: String): Result<BigDecimal> {
        val num = numberStr.toBigDecimalOrNull() ?: return Result.failure(IllegalArgumentException("Invalid number"))
        return try {
            val result = num.divide(BigDecimal("100"), 10, RoundingMode.HALF_UP)
            val stripped = if (result.compareTo(BigDecimal.ZERO) == 0) {
                BigDecimal.ZERO
            } else {
                result.stripTrailingZeros()
            }
            Result.success(stripped)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun formatDisplay(number: BigDecimal): String {
        val plain = number.stripTrailingZeros().toPlainString()
        if (plain.length > 15) {
            // Use scientific notation for very large or tiny numbers
            return DecimalFormat("0.######E0", DecimalFormatSymbols(Locale.US)).format(number)
        }
        return plain
    }
}

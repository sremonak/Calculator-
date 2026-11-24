package com.antigravity.calculator

import com.antigravity.calculator.engine.CalculatorEngine
import com.antigravity.calculator.model.CalculatorOperation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class CalculatorEngineTest {

    @Test
    fun testAdditionPrecision() {
        val result = CalculatorEngine.calculate("0.1", "0.2", CalculatorOperation.Add)
        assertTrue(result.isSuccess)
        assertEquals("0.3", result.getOrNull()?.toPlainString())
    }

    @Test
    fun testSubtraction() {
        val result = CalculatorEngine.calculate("10", "4.5", CalculatorOperation.Subtract)
        assertTrue(result.isSuccess)
        assertEquals("5.5", result.getOrNull()?.toPlainString())
    }

    @Test
    fun testMultiplication() {
        val result = CalculatorEngine.calculate("2.5", "4", CalculatorOperation.Multiply)
        assertTrue(result.isSuccess)
        assertEquals("10", result.getOrNull()?.toPlainString())
    }

    @Test
    fun testDivision() {
        val result = CalculatorEngine.calculate("10", "4", CalculatorOperation.Divide)
        assertTrue(result.isSuccess)
        assertEquals("2.5", result.getOrNull()?.toPlainString())
    }

    @Test
    fun testDivisionByZeroFailsGracefully() {
        val result = CalculatorEngine.calculate("5", "0", CalculatorOperation.Divide)
        assertTrue(result.isFailure)
        assertEquals("Cannot divide by zero", result.exceptionOrNull()?.message)
    }

    @Test
    fun testPercentage() {
        val result = CalculatorEngine.applyPercentage("250")
        assertTrue(result.isSuccess)
        assertEquals("2.5", result.getOrNull()?.toPlainString())
    }

    @Test
    fun testZeroCalculation() {
        val result = CalculatorEngine.calculate("5", "5", CalculatorOperation.Subtract)
        assertTrue(result.isSuccess)
        assertEquals("0", result.getOrNull()?.toPlainString())
    }
}

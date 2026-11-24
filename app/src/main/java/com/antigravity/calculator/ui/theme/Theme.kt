package com.antigravity.calculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = KeyOperatorBg,
    onPrimary = KeyOperatorText,
    secondary = KeyFunctionBg,
    onSecondary = KeyFunctionText,
    background = DarkBackground,
    onBackground = DisplayPrimaryText,
    surface = DarkSurface,
    onSurface = DisplayPrimaryText,
    error = ErrorRed
)

@Composable
fun HumanCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Calculators are inherently optimal with dark high-contrast themes for readability and battery
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}

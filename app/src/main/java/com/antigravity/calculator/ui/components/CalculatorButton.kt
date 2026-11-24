package com.antigravity.calculator.ui.components

import android.view.HapticFeedbackConstants
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antigravity.calculator.ui.theme.DarkCardBorder

@Composable
fun CalculatorButton(
    symbol: String,
    spokenLabel: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
    isWide: Boolean = false,
    onClick: () -> Unit
) {
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Smooth press feedback with human-perceivable contrast change
    val animatedBg by animateColorAsState(
        targetValue = if (isPressed) backgroundColor.copy(alpha = 0.8f) else backgroundColor,
        animationSpec = tween(durationMillis = 100),
        label = "button_bg_press"
    )

    Surface(
        onClick = {
            // Vibro-tactile feedback for physical keyboard feel
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
            onClick()
        },
        modifier = modifier
            .padding(5.dp)
            .then(if (isWide) Modifier else Modifier.aspectRatio(1f))
            .semantics {
                this.role = Role.Button
                this.contentDescription = spokenLabel
            },
        shape = RoundedCornerShape(24.dp),
        color = animatedBg,
        border = BorderStroke(1.dp, DarkCardBorder),
        interactionSource = interactionSource,
        shadowElevation = if (isPressed) 1.dp else 3.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = symbol,
                color = textColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

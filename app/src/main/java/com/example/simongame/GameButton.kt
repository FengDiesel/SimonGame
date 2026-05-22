package com.example.simongame

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GameButton(
    color: Color,
    modifier: Modifier,
    phase: GamePhase,
    activeColor: String,
    onClickedColor: (String) -> Unit
) {
    val colorString = when(color){
        Color.Red -> "R"
        Color.Green -> "G"
        Color.Blue -> "B"
        Color.Yellow -> "Y"
        Color.Cyan -> "C"
        Color.Magenta -> "M"
        else -> "X"
    }

    val isPressed = activeColor == colorString
    val finalColor = if (isPressed) color.copy(alpha = 0.5f) else color

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(finalColor)
            .clickable (
                enabled = if(phase == GamePhase.USER) true else false,
                onClick = { onClickedColor(colorString) }
            )
    ) {}
}
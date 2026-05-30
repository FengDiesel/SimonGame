package com.example.simongame

import android.media.SoundPool
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Composable che rappresenta un singolo bottone colorato della griglia di gioco.
 * Gestisce il feedback visivo e sonoro al click.
 *
 * @param color Colore del bottone
 * @param modifier Modificatore per dimensioni
 * @param phase Fase corrente del gioco per determinare se il bottone è cliccabile
 * @param activeColor Colore attualmente attivo durante la riproduzione
 * @param onClickedColor Callback invocata con la lettera del colore premuto
 * @param onSound Callback per riprodurre il suono associato al colore
 */
@Composable
fun GameButton(
    color: Color,
    modifier: Modifier,
    phase: GamePhase,
    activeColor: String,
    onClickedColor: (String) -> Unit,
    onSound : () -> Unit
) {

    // Mappa il colore alla lettera usata nel gioco
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

    // Riduce l'alpha quando il colore è attivo per simulare la pressione
    val finalColor = if (isPressed) color.copy(alpha = 0.5f) else color

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(finalColor)
            .clickable (
                enabled = if(phase == GamePhase.USER) true else false,
                onClick = { onClickedColor(colorString); onSound()}
            )
    ) {}
}
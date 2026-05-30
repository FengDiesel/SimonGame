package com.example.simongame

import android.content.res.Configuration
import android.media.SoundPool
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.text.ifEmpty

/**
 * Schermata di avvio dove l'utente puo' comporre una sequenza di colori.
 * Gestisce il layout portrait/landscape e mantiene lo stato della giocata in corso.
 *
 * @param onEndGame Funzione invocata alla pressione del tasto "Fine Partita".
 * Fornisce in output la sequenza finale sotto forma di stringa.
 */
@Composable
fun GameScreen(gameVM: GameViewModel, onEndGame: (GameResult) -> Unit, onNullGame: () -> Unit) {
    val configuration = LocalConfiguration.current.orientation
    val phase by gameVM.gamePhase
    val isPaused by gameVM.isPaused
    val activeColor by gameVM.activeColor

    val sp = remember { SoundPool.Builder().setMaxStreams(1).build() }
    val context = LocalContext.current
    val soundMap = remember {
        mapOf(
            "R" to sp.load(context, R.raw.red, 1),
            "G" to sp.load(context, R.raw.green, 1),
            "B" to sp.load(context, R.raw.blue, 1),
            "Y" to sp.load(context, R.raw.yellow, 1),
            "C" to sp.load(context, R.raw.cyan, 1),
            "M" to sp.load(context, R.raw.magenta, 1),
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            sp.release()
        }
    }

    LaunchedEffect(activeColor) {
        if (activeColor.isNotEmpty()) {
            soundMap[activeColor]?.let { soundID ->
                sp.play(soundID, 1f, 1f, 0, 0, 1f)
            }
        }
    }

    BackHandler {
        if (phase == GamePhase.CPU || phase == GamePhase.USER) {
            gameVM.endGame()
        }
        if (gameVM.gameResult.value.gameID.isNotEmpty()) {
            onEndGame(gameVM.gameResult.value)
        } else {
            onNullGame()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        if (configuration == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                ColorGrid(
                    modifier = Modifier.weight(1f),
                    gameVM,
                    sp,
                    soundMap
                )

                Spacer(modifier = Modifier.width(10.dp))

                GameBody(
                    if(phase == GamePhase.USER) gameVM.userSequence.joinToString(" - ") else "",
                    onEndGame = { onEndGame(gameVM.gameResult.value) },
                    onNullGame,
                    modifier = Modifier.weight(1f),
                    gameVM
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ColorGrid(
                    modifier = Modifier.weight(1f),
                    gameVM,
                    sp,
                    soundMap
                )

                Spacer(modifier = Modifier.height(10.dp))

                GameBody(
                    if(phase == GamePhase.USER) gameVM.userSequence.joinToString(" - ") else "",
                    onEndGame = { onEndGame(gameVM.gameResult.value) },
                    onNullGame,
                    modifier = Modifier.weight(1f),
                    gameVM
                )
            }
        }
    }
}

/**
 * Composable che genera una matrice 3x2 di box colorati cliccabili.
 *
 * @param onColorClick Funzione invocata quando un colore viene premuto.
 * Ritorna la lettera iniziale del colore.
 * @param modifier Modificatore per gestire le dimensioni e layout.
 */
@Composable
fun ColorGrid(modifier: Modifier = Modifier, gameVM: GameViewModel, sp: SoundPool, soundMap: Map<String, Int>) {
    val sizeModifier =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Modifier.width(150.dp).height(90.dp)
        } else Modifier.width(140.dp).height(90.dp)

    val phase by gameVM.gamePhase
    val activeColor by gameVM.activeColor

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {

        Row() {
            GameButton(Color.Red, sizeModifier, phase, activeColor, onClickedColor = {gameVM.clickedColor(it)}, onSound = {
                soundMap["R"]?.let { soundID -> sp.play(soundID, 1f, 1f, 0, 0, 1f) }
            })

            Spacer(modifier = Modifier.width(6.dp))

            GameButton(Color.Blue, sizeModifier, phase, activeColor, onClickedColor = {gameVM.clickedColor(it)}, onSound = {
                soundMap["B"]?.let { soundID -> sp.play(soundID, 1f, 1f, 0, 0, 1f) }
            })
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row() {
            GameButton(Color.Cyan, sizeModifier, phase, activeColor, onClickedColor = {gameVM.clickedColor(it)}, onSound = {
                soundMap["C"]?.let { soundID -> sp.play(soundID, 1f, 1f, 0, 0, 1f) }
            })

            Spacer(modifier = Modifier.width(6.dp))

            GameButton(Color.Yellow, sizeModifier, phase, activeColor, onClickedColor = {gameVM.clickedColor(it)}, onSound = {
                soundMap["Y"]?.let { soundID -> sp.play(soundID, 1f, 1f, 0, 0, 1f) }
            })
        }
        Spacer(modifier = Modifier.height(6.dp))

        Row() {
            GameButton(Color.Magenta, sizeModifier, phase, activeColor, onClickedColor = {gameVM.clickedColor(it)}, onSound = {
                soundMap["M"]?.let { soundID -> sp.play(soundID, 1f, 1f, 0, 0, 1f) }
            })

            Spacer(modifier = Modifier.width(10.dp))

            GameButton(Color.Green, sizeModifier, phase, activeColor, onClickedColor = {gameVM.clickedColor(it)}, onSound = {
                soundMap["G"]?.let { soundID -> sp.play(soundID, 1f, 1f, 0, 0, 1f) }
            })
        }
    }
}

/**
 * Composable che genera l'area di testo della sequenza e i bottoni.
 *
 * @param sequence Stringa della sequenza di colori in corso.
 * @param onEndGame Funzione invocata per terminare la partita.
 * @param onClear Funzione invocata per svuotare la sequenza.
 * @param modifier Modificatore per gestire le dimensioni e layout.
 */
@Composable
fun GameBody(
    sequence: String,
    onEndGame: (GameResult) -> Unit,
    onNullGame: () -> Unit,
    modifier: Modifier = Modifier,
    gameVM: GameViewModel
) {
    val phase by gameVM.gamePhase

    var start = false
    var end = false
    var pause = false

    if(phase == GamePhase.STATIC){
        start = true
        end = false
        pause = false
    }else if(phase == GamePhase.CPU){
        start = false
        end = true
        pause = true
    }else if(phase == GamePhase.USER){
        start = false
        end = true
        pause = false
    }else if(phase == GamePhase.ERROR){
        start = false
        end = false
        pause = false
    }

    Spacer(modifier = Modifier.height(10.dp))

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val text = when (phase) {
                GamePhase.ERROR -> R.string.error
                GamePhase.STATIC -> R.string.start_sequence
                GamePhase.CPU -> R.string.cpu
                else -> R.string.no_text
            }

        Text(
            text = if(phase == GamePhase.USER) sequence else stringResource(text),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .clip(RoundedCornerShape(30.dp))
                .padding(8.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Button(onClick = { gameVM.startGame() }, enabled = start) {
                Text(text = stringResource(R.string.start_game))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = { gameVM.togglePause() }, enabled = pause) {
                Text(text = if(gameVM.isPaused.value) stringResource(R.string.play) else stringResource(R.string.pause))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    gameVM.endGame()
                    if (gameVM.gameResult.value.gameID.isNotEmpty()) {
                        onEndGame(gameVM.gameResult.value)
                    } else {
                        onNullGame()
                    }
                },
                enabled = end
            ) { Text(text = stringResource(R.string.end_game)) }
        }
    }
}


@Preview
@Composable
fun GameScreenPreview() {
    //GameScreen()
}
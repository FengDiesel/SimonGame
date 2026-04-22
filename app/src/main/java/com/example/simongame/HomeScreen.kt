package com.example.simongame

import android.content.res.Configuration
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import kotlin.text.ifEmpty

/**
 * Schermata di avvio dove l'utente puo' comporre una sequenza di colori.
 * Gestisce il layout portrait/landscape e
 * mantiene lo stato della giocata in corso.
 *
 * @param onEndGame Funzione invocata alla pressione del tasto "Fine Partita".
 * Fornisce in output la sequenza finale sotto forma di stringa.
 */
@Composable
fun HomeScreen(onEndGame: (String) -> Unit) {
    val configuration = LocalConfiguration.current.orientation

    var sequence by rememberSaveable { mutableStateOf("") }

    fun addColor(letter: String) {
        if (sequence.isEmpty()) {
            sequence = letter
        } else {
            sequence += " - $letter"
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
                    onColorClick = { color -> addColor(color) },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(10.dp))

                GameBody(
                    sequence,
                    onEndGame = { onEndGame(sequence); sequence = "" },
                    onClear = { sequence = "" },
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ColorGrid(
                    onColorClick = { color -> addColor(color) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                GameBody(
                    sequence,
                    onEndGame = { onEndGame(sequence); sequence = "" },
                    onClear = { sequence = "" }
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
fun ColorGrid(onColorClick: (String) -> Unit, modifier: Modifier = Modifier) {
    val sizeModifier =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Modifier.size(130.dp)
        } else Modifier.size(90.dp)

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {

        Row() {
            Box(
                modifier = sizeModifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.Red)
                    .clickable { onColorClick("R") }
            ) {}

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = sizeModifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.Blue)
                    .clickable { onColorClick("B") }
            ) {}
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Box(
                modifier = sizeModifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.Cyan)
                    .clickable { onColorClick("C") }
            ) {}

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = sizeModifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.Yellow)
                    .clickable { onColorClick("Y") }
            ) {}
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Box(
                modifier = sizeModifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.Magenta)
                    .clickable { onColorClick("M") }
            ) {}

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = sizeModifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.Green)
                    .clickable { onColorClick("G") }
            ) {}
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
    onEndGame: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = sequence.ifEmpty { stringResource(R.string.start_sequence) },
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .clip(RoundedCornerShape(30.dp))
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Button(onClick = { onClear() }) {
                Text(text = stringResource(R.string.delete))
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(onClick = { onEndGame(sequence) }) {
                Text(text = stringResource(R.string.end_game))
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onEndGame = {}
    )
}
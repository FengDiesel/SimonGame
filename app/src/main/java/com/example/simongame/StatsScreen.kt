package com.example.simongame

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Schermata finale che mostra la lista delle partite concluse.
 *
 * @param history Lista di stringhe che contenente lo storico di tutte le giocate.
 * @param onBackPress Funzione invocata per intercettare il tasto "back" di sistema
 * e tornare alla schermata principale.
 */
@Composable
fun StatsScreen(history: List<GameResult>, onGameScreen: () -> Unit, onGameDetail: (String) -> Unit) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onGameScreen() }) {
                Icon(Icons.Default.PlayArrow, contentDescription = stringResource(R.string.start_game))
            }
        }
    ){ innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = innerPadding,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(R.string.score),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            items(history) { game ->
                val sequence = game.sequence;
                val letters = sequence.split(" - ")

                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (sequence.isEmpty()) "0" else game.maxCorrectLength.toString(),
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = if (sequence.isEmpty()) stringResource(R.string.empty_play) else sequence,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onGameDetail(game.gameID) }
                    )
                }

                HorizontalDivider(thickness = 2.dp)
            }
        }
    }
}

@Preview
@Composable
fun StatsScreenPreview() {
    //StatsScreen()
}
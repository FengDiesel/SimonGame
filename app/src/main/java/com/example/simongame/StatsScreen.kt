package com.example.simongame

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Schermata principale che mostra la lista delle partite concluse.
 * Ogni elemento mostra il punteggio massimo e la sequenza, evidenziando il punto di errore.
 * con la parte errata evidenziata in rosso.
 *
 * @param history Lista delle partite concluse
 * @param onGameScreen Callback per navigare alla schermata di gioco
 * @param onGameDetail Callback per navigare al dettaglio di una partita, fornisce il gameID
 */

/**
 * Schermata principale che mostra la lista delle partite concluse.
 * Ogni elemento mostra il punteggio massimo e la sequenza, evidenziando il punto di errore.
 *
 * @param history Lista di [GameResult] contenente le partite concluse.
 * @param onGameScreen Funzione per navigare alla schermata di gioco
 * @param onGameDetail Funzione per navigare alla schermata di dettaglio di una partita
 */
@Composable
fun StatsScreen(history: List<GameResult>, onGameScreen: () -> Unit, onGameDetail: (String) -> Unit) {

    Scaffold(
        // Floating Action Button per navigare alla schermata di gioco e iniziare una nuova partita
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

            // Genera un elemento della lista per ogni partita conclusa contenuta in history
            items(history) { game ->
                val sequence = game.sequence;

                Row(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f).clickable { onGameDetail(game.gameID) }
                    ){
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(45.dp)
                                .background(color = Color.LightGray)
                        ) {
                            Text(
                                text = if (sequence.isEmpty()) "0" else game.maxCorrectLength.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        // Mostra la sequenza se presente, altrimenti un testo placeholder, con relativa logica dello sfondo per visualizzare il punto di errore
                        if(!sequence.isEmpty()){
                            sequence.split(" - ").forEachIndexed { index, element ->
                                val background = if(index <= game.currentCorrectLength-1) Color.Green else Color.Red

                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(45.dp)
                                        .background(color = background)
                                ) {
                                    Text(
                                        text = element,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }
                        }else{
                            Text(
                                text = stringResource(R.string.empty_play)
                            )
                        }
                    }
                }

                HorizontalDivider(thickness = 2.dp)
            }
        }
    }
}

@Preview
@Composable
fun StatsScreenPreview() { }
package com.example.simongame

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Classe che rappresenta una partita conclusa.
 * Utilizzato sia come Entity per il database Room,
 * che come oggetto di scambio tra ViewModel e UI.
 *
 * @param maxCorrectLength Lunghezza massima della sequenza riprodotta correttamente
 * @param currentCorrectLength Numero di colori corretti nell'ultimo turno prima dell'errore
 * @param sequence Sequenza completa generata dalla CPU
 * @param gameID Identificatore univoco della partita generato con UUID
 */
@Entity(tableName = "game_result")
data class GameResult(
    @ColumnInfo(name = "max_correct_length")
    val maxCorrectLength: Int,
    @ColumnInfo(name = "current_correct_length")
    val currentCorrectLength: Int,
    @ColumnInfo(name = "sequence")
    val sequence: String,
    @PrimaryKey
    var gameID: String
)
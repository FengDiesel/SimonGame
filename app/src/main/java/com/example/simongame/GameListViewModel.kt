package com.example.simongame

import androidx.compose.runtime.internal.StabilityInferred
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * ViewModel per la gestione della lista delle partite concluse.
 * Carica i dati dal database Room all'avvio e fornisce funzioni
 * per aggiungere nuove partite.
 * Il ciclo di vita è legato all'intera app tramite SimonGame().
 *
 * @param gameResultDao DAO per l'accesso al database delle partite
 */
class GameListViewModel(gameResultDao: GameResultDao) : ViewModel() {
    // Lista delle partite concluse, aggiornata sia in memoria che nel database
    val history = mutableStateListOf<GameResult>()
    private val dao = gameResultDao

    // Carica tutte le partite salvate dal database all'avvio del ViewModel
    init {
        viewModelScope.launch {
            for(element in dao.getAllResult()) history.add(element)
        }
    }

    /**
     * Aggiunge una nuova partita al database e alla lista in memoria.
     * L'inserimento avviene in una coroutine per non bloccare il thread principale.
     *
     * @param game [GameResult] da salvare
     */
    fun addGame(game: GameResult) {
        viewModelScope.launch{
            dao.insertGameResult(game)
            history.add(game)
        }
    }
}
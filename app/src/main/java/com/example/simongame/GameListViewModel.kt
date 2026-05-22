package com.example.simongame

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

class GameListViewModel : ViewModel() {
    val history = mutableStateListOf<GameResult>()

    fun addGame(game: GameResult) {
        history.add(game)
    }

    private fun calculateLength(sequence: String): Int {
        return sequence.split(" - ").size
    }
}
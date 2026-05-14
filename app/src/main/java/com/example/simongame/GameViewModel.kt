package com.example.simongame

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

class GameViewModel : ViewModel() {
    val history = mutableStateListOf<GameResult>()

    fun addGame(sequence: String) {
        val newGame = GameResult(
            maxCorrectLength = calculateLength(sequence)-1,
            sequence = sequence,
            gameID = UUID.randomUUID().toString()
        )

        history.add(newGame)
    }

    private fun calculateLength(sequence: String): Int {
        return sequence.split(" - ").size
    }
}
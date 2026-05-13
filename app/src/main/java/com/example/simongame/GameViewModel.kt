package com.example.simongame

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val history = mutableStateListOf<GameResult>()

    fun addGame(sequence: String) {
        val newGame = GameResult(
            maxCorrectLength = calculateLength(sequence)-1,
            sequence = sequence,
            time = System.currentTimeMillis()
        )

        history.add(newGame)
    }

    private fun calculateLength(sequence: String): Int {
        return sequence.split(" - ").size
    }
}
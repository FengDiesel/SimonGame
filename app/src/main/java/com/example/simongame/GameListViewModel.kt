package com.example.simongame

import androidx.compose.runtime.internal.StabilityInferred
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.UUID

class GameListViewModel(gameResultDao: GameResultDao) : ViewModel() {
    val history = mutableStateListOf<GameResult>()
    val dao = gameResultDao

    init {
        viewModelScope.launch {
            for(element in dao.getAllResult()) history.add(element)
        }
    }

    fun addGame(game: GameResult) {
        viewModelScope.launch{
            dao.insertGameResult(game)
            history.add(game)
        }
    }
}
package com.example.simongame

data class GameResult(
    val maxCorrectLength: Int,
    val sequence: String, // sequence with error
    val gameID: String
)
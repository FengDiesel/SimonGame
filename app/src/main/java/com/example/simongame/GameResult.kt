package com.example.simongame

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_result")
data class GameResult(
    @ColumnInfo(name = "max_correct_length")
    val maxCorrectLength: Int,
    @ColumnInfo(name = "current_correct_length")
    val currentCorrectLength: Int,
    @ColumnInfo(name = "sequence")
    val sequence: String, // sequence with error
    @PrimaryKey
    var gameID: String
)
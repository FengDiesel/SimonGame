package com.example.simongame

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameResultDao{
    @Insert
    suspend fun insertGameResult(gameResult: GameResult)

    @Query("SELECT * FROM game_result")
    suspend fun getAllResult(): List<GameResult>
}
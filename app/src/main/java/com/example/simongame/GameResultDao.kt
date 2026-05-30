package com.example.simongame

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO per l'accesso al database delle partite.
 * Contiene funzioni di inserimento e lettura sulla tabella game_result.
 */
@Dao
interface GameResultDao{
    /**
     * Inserisce una nuova partita nel database.
     * @param gameResult Il [GameResult] da salvare
     */
    @Insert
    suspend fun insertGameResult(gameResult: GameResult)

    /**
     * Recupera tutte le partite salvate nel database.
     * @return Lista di tutti i [GameResult] presenti
     */
    @Query("SELECT * FROM game_result")
    suspend fun getAllResult(): List<GameResult>
}
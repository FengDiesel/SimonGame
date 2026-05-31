package com.example.simongame

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Database Room dell'app.
 * Contiene la tabella delle partite concluse e fornisce l'accesso al DAO.
 */
@Database(entities = [GameResult::class], version = 2)
abstract class SimonDatabase: RoomDatabase(){
    abstract fun gameResultDao(): GameResultDao

    companion object {
        // Garantisce la visibilità delle modifiche tra thread
        @Volatile
        private var INSTANCE: SimonDatabase? = null

        // Restituisce l'istanza esistente o ne crea una nuova in modo thread-safe
        fun getDatabase(context: Context): SimonDatabase {

            // synchronized garantisce che un solo thread alla volta possa creare l'istanza
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    SimonDatabase::class.java,
                    "simon_game_database"
                )

                    // in caso di migrazione distruttiva ricrea il database perdendo i dati esistenti
                    .fallbackToDestructiveMigration(false)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
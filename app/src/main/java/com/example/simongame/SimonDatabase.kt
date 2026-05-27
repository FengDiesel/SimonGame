package com.example.simongame

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameResult::class], version = 2)
abstract class SimonDatabase: RoomDatabase(){
    abstract fun gameResultDao(): GameResultDao

    companion object {
        @Volatile
        private var INSTANCE: SimonDatabase? = null

        fun getDatabase(context: Context): SimonDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    SimonDatabase::class.java,
                    "simon_game_database"
                )

                    .fallbackToDestructiveMigration(false)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
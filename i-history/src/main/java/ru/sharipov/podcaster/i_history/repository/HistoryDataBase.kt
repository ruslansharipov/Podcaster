package ru.sharipov.podcaster.i_history.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        const val NAME = "podcaster_database"
    }
}
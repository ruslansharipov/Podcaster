package ru.sharipov.podcaster.i_history

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.sharipov.podcaster.i_history.dao.HistoryDao
import ru.sharipov.podcaster.i_history.dao.SubscriptionDao
import ru.sharipov.podcaster.i_history.entity.HistoryEntity
import ru.sharipov.podcaster.i_history.entity.SubscriptionEntity

@Database(entities = [HistoryEntity::class, SubscriptionEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    abstract fun subscriptionDao(): SubscriptionDao

    companion object {
        const val NAME = "podcaster_database"
    }
}
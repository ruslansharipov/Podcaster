package ru.sharipov.podcaster.base_feature.application.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.sharipov.podcaster.i_history.AppDataBase
import ru.sharipov.podcaster.i_history.dao.HistoryDao
import ru.sharipov.podcaster.i_history.dao.SubscriptionDao
import ru.surfstudio.android.dagger.scope.PerApplication

@Module
class DatabaseModule {

    @Provides
    @PerApplication
    fun provideDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            AppDataBase.NAME
        ).build()
    }

    @Provides
    @PerApplication
    fun provideHistoryDao(db: AppDataBase): HistoryDao {
        return db.historyDao()
    }

    @Provides
    @PerApplication
    fun provideSubscriptionDao(db: AppDataBase): SubscriptionDao {
        return db.subscriptionDao()
    }
}
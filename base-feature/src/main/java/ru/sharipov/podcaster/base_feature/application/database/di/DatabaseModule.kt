package ru.sharipov.podcaster.base_feature.application.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.sharipov.podcaster.i_history.repository.HistoryDataBase
import ru.sharipov.podcaster.i_history.repository.HistoryDao
import ru.surfstudio.android.dagger.scope.PerApplication

@Module
class DatabaseModule {

    @Provides
    @PerApplication
    fun provideDataBase(context: Context): HistoryDataBase {
        return Room.databaseBuilder(
            context,
            HistoryDataBase::class.java,
            HistoryDataBase.NAME
        ).build()
    }

    @Provides
    @PerApplication
    fun provideHistoryDao(db: HistoryDataBase): HistoryDao {
        return db.historyDao()
    }
}
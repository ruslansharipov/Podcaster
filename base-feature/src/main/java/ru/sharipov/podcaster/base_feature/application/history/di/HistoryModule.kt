package ru.sharipov.podcaster.base_feature.application.history.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.sharipov.podcaster.i_history.HistoryStorage
import ru.sharipov.podcaster.i_history.LastPlayedStorage
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider

@Module
class HistoryModule {

    @Provides
    @PerApplication
    fun provideHistoryStorage(
        context: Context,
        namingProcessor: NamingProcessor
    ): HistoryStorage {
        return HistoryStorage(
            cacheDir = AppDirectoriesProvider.provideNoBackupStorageDir(context),
            namingProcessor = namingProcessor
        )
    }

    @Provides
    @PerApplication
    fun provideLastPlayedStorage(
        context: Context,
        namingProcessor: NamingProcessor
    ): LastPlayedStorage {
        return LastPlayedStorage(
            cacheDir = AppDirectoriesProvider.provideNoBackupStorageDir(context),
            namingProcessor = namingProcessor
        )
    }
}
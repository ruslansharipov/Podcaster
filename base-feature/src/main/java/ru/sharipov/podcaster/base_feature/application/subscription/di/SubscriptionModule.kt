package ru.sharipov.podcaster.base_feature.application.subscription.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.sharipov.podcaster.i_subscription.SubscriptionStorage
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider

@Module
class SubscriptionModule {

    @PerApplication
    @Provides
    fun providesSubscriptionStorage(
        context: Context,
        namingProcessor: NamingProcessor
    ): SubscriptionStorage {
        return SubscriptionStorage(
            cacheDir = AppDirectoriesProvider.provideNoBackupStorageDir(context),
            namingProcessor = namingProcessor
        )
    }
}
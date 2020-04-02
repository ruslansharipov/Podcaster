package ru.sharipov.podcaster.base_feature.application.cache.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.sharipov.podcaster.base.naming.DefaultNamingProcessor
import ru.sharipov.podcaster.i_network.ServerUrls
import ru.sharipov.podcaster.i_network.cache.SimpleCacheInfoStorage
import ru.sharipov.podcaster.i_network.network.cache.SimpleCacheFactory
import ru.sharipov.podcaster.i_network.network.cache.SimpleCacheInterceptor
import ru.sharipov.podcaster.i_network.network.cache.SimpleCacheUrlConnector
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider
import ru.surfstudio.android.filestorage.naming.NamingProcessor

/**
 * Dagger-модуль для удовлетворения зависимостей классов, использующихся для кэширования
 */
@Module
class CacheModule {

    @Provides
    @PerApplication
    internal fun provideSimpleCacheInterceptor(
        simpleCacheFactory: SimpleCacheFactory,
        simpleCacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheInterceptor {
        return SimpleCacheInterceptor(simpleCacheFactory, simpleCacheUrlConnector)
    }

    @Provides
    @PerApplication
    internal fun provideSimpleCacheFactory(
            context: Context,
            cacheUrlConnector: SimpleCacheUrlConnector
    ): SimpleCacheFactory {
        return SimpleCacheFactory(AppDirectoriesProvider.provideBackupStorageDir(context), cacheUrlConnector)
    }

    @Provides
    @PerApplication
    internal fun providesSimpleCacheConnector(
            simpleCacheInfoStorage: SimpleCacheInfoStorage
    ): SimpleCacheUrlConnector {
        return SimpleCacheUrlConnector(ServerUrls.BASE_URL, simpleCacheInfoStorage.simpleCaches)
    }

    @Provides
    @PerApplication
    fun provideNamingProcessor(): NamingProcessor = DefaultNamingProcessor()
}
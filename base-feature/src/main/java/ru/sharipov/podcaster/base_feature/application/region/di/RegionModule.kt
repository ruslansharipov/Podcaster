package ru.sharipov.podcaster.base_feature.application.region.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.sharipov.podcaster.i_genres.RegionApi
import ru.sharipov.podcaster.i_genres.RegionStorage
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.utils.AppDirectoriesProvider

@Module
class RegionModule {

    @PerApplication
    @Provides
    fun provideRegionStorage(
        context: Context,
        namingProcessor: NamingProcessor
    ): RegionStorage {
        return RegionStorage(AppDirectoriesProvider.provideNoBackupStorageDir(context), namingProcessor)
    }

    @PerApplication
    @Provides
    fun provideRegionApi(retrofit: Retrofit) : RegionApi {
        return retrofit.create(RegionApi::class.java)
    }
}
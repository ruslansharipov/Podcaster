package ru.sharipov.podcaster.base_feature.application.podcast.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.sharipov.podcaster.i_listen.ListenApi
import ru.surfstudio.android.dagger.scope.PerApplication

@Module
class PodcastModule {

    @Provides
    @PerApplication
    fun providesListenApi(retrofit: Retrofit) : ListenApi {
        return retrofit.create(ListenApi::class.java)
    }
}
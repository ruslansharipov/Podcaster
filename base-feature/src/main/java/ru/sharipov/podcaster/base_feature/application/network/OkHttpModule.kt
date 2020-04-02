package ru.sharipov.podcaster.base_feature.application.network

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.sharipov.podcaster.i_network.network.cache.SimpleCacheInterceptor
import ru.surfstudio.android.dagger.scope.PerApplication
import java.util.concurrent.TimeUnit

private const val NETWORK_TIMEOUT = 30L //sec

@Module
class OkHttpModule {

    @Provides
    @PerApplication
    internal fun provideOkHttpClient(
            cacheInterceptor: SimpleCacheInterceptor,
            httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(cacheInterceptor)
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }
}

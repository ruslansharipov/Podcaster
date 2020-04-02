package ru.sharipov.podcaster.base_feature.application.network

import android.app.DownloadManager
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sharipov.podcaster.i_network.ServerUrls
import ru.sharipov.podcaster.i_network.network.CallAdapterFactory
import ru.sharipov.podcaster.i_network.network.calladapter.BaseCallAdapterFactory
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.logger.Logger

@Module
class NetworkModule {

    private companion object {
        const val HTTP_LOG_TAG = "OkHttp"
    }

    @Provides
    @PerApplication
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        callAdapterFactory: BaseCallAdapterFactory,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ServerUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }

    @Provides
    @PerApplication
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @PerApplication
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Logger.d("$HTTP_LOG_TAG $message")
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @PerApplication
    internal fun provideDownloadManager(context: Context): DownloadManager {
        return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    @Provides
    @PerApplication
    internal fun provideCallAdapterFactory(): BaseCallAdapterFactory {
        return CallAdapterFactory()
    }
}

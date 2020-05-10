package ru.sharipov.podcaster.base_feature.application.app.di

import dagger.Component
import ru.sharipov.podcaster.base_feature.application.cache.di.CacheModule
import ru.sharipov.podcaster.base_feature.application.network.NetworkModule
import ru.sharipov.podcaster.base_feature.application.network.OkHttpModule
import ru.sharipov.podcaster.base_feature.application.podcast.di.PodcastModule
import ru.sharipov.podcaster.base_feature.application.region.di.RegionModule
import ru.sharipov.podcaster.base_feature.application.storage.di.SharedPrefModule
import ru.sharipov.podcaster.base_feature.application.subscription.di.SubscriptionModule
import ru.surfstudio.android.dagger.scope.PerApplication

// PerApplication модули здесь, в том числе интеракторы
@PerApplication
@Component(
    modules = [
        AppModule::class,
        SharedPrefModule::class,
//        SearchHintsCacheModule::class,
        CacheModule::class,
        NetworkModule::class,
        OkHttpModule::class,
        PodcastModule::class,
        RegionModule::class,
        SubscriptionModule::class
    ]
)
interface AppComponent : AppProxyDependencies

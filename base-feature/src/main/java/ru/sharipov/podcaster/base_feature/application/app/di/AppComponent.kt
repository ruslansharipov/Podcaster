package ru.sharipov.podcaster.base_feature.application.app.di

import dagger.Component
import ru.sharipov.podcaster.base_feature.application.cache.di.CacheModule
import ru.sharipov.podcaster.base_feature.application.network.NetworkModule
import ru.sharipov.podcaster.base_feature.application.network.OkHttpModule
import ru.sharipov.podcaster.base_feature.application.storage.di.SharedPrefModule
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
        OkHttpModule::class
    ]
)
interface AppComponent : AppProxyDependencies

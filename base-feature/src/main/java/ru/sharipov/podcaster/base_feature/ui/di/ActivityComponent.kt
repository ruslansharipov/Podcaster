package ru.sharipov.podcaster.base_feature.ui.di

import dagger.Component
import ru.surfstudio.android.dagger.scope.PerActivity
import ru.sharipov.podcaster.base_feature.application.app.di.AppProxyDependencies
import ru.sharipov.podcaster.base_feature.application.app.di.AppComponent

/**
 * Компонент для @[PerActivity] скоупа
 */
@PerActivity
@Component(
        dependencies = [AppComponent::class],
        modules = [ActivityModule::class]
)
interface ActivityComponent : AppProxyDependencies, ActivityProxyDependencies
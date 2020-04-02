package ru.sharipov.podcaster.f_splash

import android.content.Intent
import dagger.Component
import dagger.Module
import ru.sharipov.podcaster.base_feature.ui.di.ActivityScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.screen.ActivityScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class SplashActivityConfigurator(intent: Intent) : ActivityScreenConfigurator(intent) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [ActivityScreenModule::class, SplashScreenModule::class]
    )
    interface SplashActivityComponent : ScreenComponent<SplashActivityView>

    @Module
    class SplashScreenModule(route: SplashRoute) : CustomScreenModule<SplashRoute>(route)

    override fun createScreenComponent(
        activityComponent: ActivityComponent,
        activityScreenModule: ActivityScreenModule,
        intent: Intent
    ): ScreenComponent<*> {
        @Suppress("DEPRECATION")
        return DaggerSplashActivityConfigurator_SplashActivityComponent.builder()
            .activityComponent(activityComponent)
            .activityScreenModule(activityScreenModule)
            .splashScreenModule(SplashScreenModule(SplashRoute()))
            .build()
    }
}
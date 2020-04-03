package ru.sharipov.podcaster.f_main.di

import android.content.Intent
import dagger.Component
import dagger.Module
import ru.sharipov.podcaster.base_feature.ui.di.ActivityScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.navigation.MainActivityRoute
import ru.sharipov.podcaster.base_feature.ui.screen.ActivityScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.f_main.DaggerMainActivityConfigurator_MainActivityComponent
import ru.sharipov.podcaster.f_main.MainActivityView
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class MainActivityConfigurator(intent: Intent): ActivityScreenConfigurator(intent) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [ActivityScreenModule::class, MainActivityModule::class]
    )
    interface MainActivityComponent : ScreenComponent<MainActivityView>

    @Module
    internal class MainActivityModule(route: MainActivityRoute) : CustomScreenModule<MainActivityRoute>(route)

    @Suppress("DEPRECATION")
    override fun createScreenComponent(
        parentComponent: ActivityComponent,
        activityScreenModule: ActivityScreenModule,
        intent: Intent?
    ): ScreenComponent<*> {
        return DaggerMainActivityConfigurator_MainActivityComponent.builder()
            .activityComponent(parentComponent)
            .activityScreenModule(activityScreenModule)
            .mainActivityModule(
                MainActivityModule(
                    MainActivityRoute()
                )
            )
            .build()
    }

}
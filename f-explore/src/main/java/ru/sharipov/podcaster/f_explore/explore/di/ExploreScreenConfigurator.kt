package ru.sharipov.podcaster.f_explore.explore.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.di.FragmentScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.navigation.ExploreFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.sharipov.podcaster.f_explore.explore.ExploreFragmentView
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class ExploreScreenConfigurator : FragmentScreenConfigurator(Bundle()) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, ExploreScreenModule::class]
    )
    interface ExploreScreenComponent : ScreenComponent<ExploreFragmentView>

    @Module
    class ExploreScreenModule(route: ExploreFragmentRoute): CustomScreenModule<ExploreFragmentRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerExploreScreenConfigurator_ExploreScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .exploreScreenModule(
                ExploreScreenModule(
                    ExploreFragmentRoute()
                )
            )
            .build()
    }

}

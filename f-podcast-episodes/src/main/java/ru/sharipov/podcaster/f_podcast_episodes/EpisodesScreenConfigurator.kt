package ru.sharipov.podcaster.f_podcast_episodes

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.di.FragmentScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodesFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class EpisodesScreenConfigurator(args: Bundle?) : FragmentScreenConfigurator(args ?: Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, EpisodesScreenModule::class]
    )
    internal interface EpisodesScreenComponent : ScreenComponent<EpisodesFragmentView>

    @Module
    internal class EpisodesScreenModule(route: EpisodesFragmentRoute) :
        CustomScreenModule<EpisodesFragmentRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerEpisodesScreenConfigurator_EpisodesScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .episodesScreenModule(EpisodesScreenModule(EpisodesFragmentRoute(args)))
            .build()
    }
}
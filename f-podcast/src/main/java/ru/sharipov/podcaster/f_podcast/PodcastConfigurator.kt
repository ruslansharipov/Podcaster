package ru.sharipov.podcaster.f_podcast

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.di.FragmentScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class PodcastScreenConfigurator(args: Bundle?) : FragmentScreenConfigurator(args ?: Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, PodcastScreenModule::class]
    )
    internal interface PodcastScreenComponent : ScreenComponent<PodcastFragmentView>

    @Module
    internal class PodcastScreenModule(route: PodcastFragmentRoute) :
        CustomScreenModule<PodcastFragmentRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerPodcastScreenConfigurator_PodcastScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .podcastScreenModule(PodcastScreenModule(PodcastFragmentRoute(args)))
            .build()
    }
}

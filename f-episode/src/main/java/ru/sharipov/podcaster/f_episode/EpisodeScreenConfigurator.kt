package ru.sharipov.podcaster.f_episode

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.rendezvous.app.ui.activity.di.BottomDialogScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.di.FragmentScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class EpisodeScreenConfigurator(args: Bundle?) : BottomDialogScreenConfigurator(args ?: Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, EpisodeScreenModule::class]
    )
    interface EpisodeScreenComponent : ScreenComponent<EpisodeFragmentView>

    @Module
    class EpisodeScreenModule(route: EpisodeFragmentRoute): CustomScreenModule<EpisodeFragmentRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle
    ): ScreenComponent<*> {
        return DaggerEpisodeScreenConfigurator_EpisodeScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .episodeScreenModule(EpisodeScreenModule(EpisodeFragmentRoute(args)))
            .build()
    }

}

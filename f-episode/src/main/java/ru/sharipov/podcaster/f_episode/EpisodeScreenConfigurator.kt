package ru.sharipov.podcaster.f_episode

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.rendezvous.app.ui.activity.di.BottomDialogScreenConfigurator
import ru.rendezvous.app.ui.activity.di.DialogScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeDialogRoute
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
    interface EpisodeScreenComponent : ScreenComponent<EpisodeDialogView>

    @Module
    class EpisodeScreenModule(route: EpisodeDialogRoute): CustomScreenModule<EpisodeDialogRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle
    ): ScreenComponent<*> {
        return DaggerEpisodeScreenConfigurator_EpisodeScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .episodeScreenModule(EpisodeScreenModule(EpisodeDialogRoute(args)))
            .build()
    }

}

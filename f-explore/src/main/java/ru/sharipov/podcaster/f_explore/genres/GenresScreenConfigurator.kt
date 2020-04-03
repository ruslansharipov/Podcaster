package ru.sharipov.podcaster.f_explore.genres

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.di.FragmentScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class GenresScreenConfigurator : FragmentScreenConfigurator(Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, GenresScreenModule::class]
    )
    interface GenresScreenComponent : ScreenComponent<GenresFragmentView>

    @Module
    class GenresScreenModule(route: GenresFragmentRoute) :
        CustomScreenModule<GenresFragmentRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerGenresScreenConfigurator_GenresScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .genresScreenModule(GenresScreenModule(GenresFragmentRoute()))
            .build()
    }
}
package ru.sharipov.podcast.f_curated_list.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.sharipov.podcast.f_curated_list.CuratedListFragmentView
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.di.FragmentScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.navigation.CuratedListFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class CuratedListScreenConfigurator : FragmentScreenConfigurator(Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, CuratedListScreenModule::class]
    )
    interface CuratedListScreenComponent : ScreenComponent<CuratedListFragmentView>

    @Module
    class CuratedListScreenModule(route: CuratedListFragmentRoute) :
        CustomScreenModule<CuratedListFragmentRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerCuratedListScreenConfigurator_CuratedListScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .curatedListScreenModule(CuratedListScreenModule(CuratedListFragmentRoute()))
            .build()
    }

}

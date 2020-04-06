package ru.sharipov.podcaster.f_search.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.di.FragmentScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.navigation.SearchFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.sharipov.podcaster.f_search.SearchFragmentView
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class SearchScreenConfigurator : FragmentScreenConfigurator(Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, SearchScreenModule::class]
    )
    internal interface SearchScreenComponent : ScreenComponent<SearchFragmentView>

    @Module
    internal class SearchScreenModule(route: SearchFragmentRoute) :
        CustomScreenModule<SearchFragmentRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerSearchScreenConfigurator_SearchScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .searchScreenModule(
                SearchScreenModule(
                    SearchFragmentRoute()
                )
            )
            .build()
    }
}


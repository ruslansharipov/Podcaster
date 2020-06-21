package ru.sharipov.podcaster.f_search.di

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.rendezvous.app.ui.activity.di.DialogScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.navigation.SearchDialogRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.sharipov.podcaster.f_search.SearchDialogView
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class SearchScreenConfigurator : DialogScreenConfigurator(Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, SearchScreenModule::class]
    )
    internal interface SearchScreenComponent : ScreenComponent<SearchDialogView>

    @Module
    internal class SearchScreenModule(route: SearchDialogRoute) :
        CustomScreenModule<SearchDialogRoute>(route)

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
                    SearchDialogRoute()
                )
            )
            .build()
    }
}


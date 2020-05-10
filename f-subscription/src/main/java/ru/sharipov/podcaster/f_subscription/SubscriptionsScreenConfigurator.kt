package ru.sharipov.podcaster.f_subscription

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.di.FragmentScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.navigation.SubscriptionsFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class SubscriptionsScreenConfigurator : FragmentScreenConfigurator(Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, SubscriptionsScreenModule::class]
    )
    interface SubscriptionsScreenComponent : ScreenComponent<SubscriptionsFragmentView>

    @Module
    class SubscriptionsScreenModule(route: SubscriptionsFragmentRoute): CustomScreenModule<SubscriptionsFragmentRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle
    ): ScreenComponent<*> {
        return DaggerSubscriptionsScreenConfigurator_SubscriptionsScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .subscriptionsScreenModule(SubscriptionsScreenModule(SubscriptionsFragmentRoute()))
            .build()
    }

}

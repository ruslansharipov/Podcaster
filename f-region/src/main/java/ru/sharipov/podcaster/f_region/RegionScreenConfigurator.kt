package ru.sharipov.podcaster.f_region

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.rendezvous.app.ui.activity.di.BottomDialogScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.navigation.RegionDialogRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class RegionScreenConfigurator : BottomDialogScreenConfigurator(Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, RegionScreenModule::class]
    )
    interface RegionScreenComponent : ScreenComponent<RegionDialogView>

    @Module
    class RegionScreenModule(route: RegionDialogRoute): CustomScreenModule<RegionDialogRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerRegionScreenConfigurator_RegionScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .regionScreenModule(RegionScreenModule(RegionDialogRoute()))
            .build()
    }

}
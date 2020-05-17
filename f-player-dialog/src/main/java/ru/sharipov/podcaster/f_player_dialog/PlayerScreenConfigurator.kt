package ru.sharipov.podcaster.f_player_dialog

import android.os.Bundle
import dagger.Component
import dagger.Module
import ru.rendezvous.app.ui.activity.di.DialogScreenConfigurator
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.navigation.PlayerDialogRoute
import ru.sharipov.podcaster.base_feature.ui.screen.CustomScreenModule
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.dagger.scope.PerScreen

class PlayerScreenConfigurator : DialogScreenConfigurator(Bundle.EMPTY) {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, PlayerScreenModule::class]
    )
    interface PlayerScreenComponent : ScreenComponent<PlayerDialogView>

    @Module
    class PlayerScreenModule(route: PlayerDialogRoute): CustomScreenModule<PlayerDialogRoute>(route)

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        fragmentScreenModule: FragmentScreenModule?,
        args: Bundle?
    ): ScreenComponent<*> {
        return DaggerPlayerScreenConfigurator_PlayerScreenComponent.builder()
            .activityComponent(parentComponent)
            .fragmentScreenModule(fragmentScreenModule)
            .playerScreenModule(PlayerScreenModule(PlayerDialogRoute()))
            .build()
    }

}
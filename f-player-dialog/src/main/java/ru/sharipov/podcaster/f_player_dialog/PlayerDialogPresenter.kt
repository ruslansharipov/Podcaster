package ru.sharipov.podcaster.f_player_dialog

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerInteractor
import ru.sharipov.podcaster.base_feature.ui.navigation.PlayerDialogRoute
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigator
import javax.inject.Inject

@PerScreen
class PlayerDialogPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val route: PlayerDialogRoute,
    private val reducer: PlayerReducer,
    private val dialogNavigator: DialogNavigator,
    private val playerInteractor: PlayerInteractor
) : StatePresenter(dependency) {

    init {
        playerInteractor
            .observeAllStates()
            .subscribeIoDefault(reducer::onStateChange)
    }
}
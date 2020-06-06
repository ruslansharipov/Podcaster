package ru.sharipov.podcaster.f_player_dialog

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerInteractor
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.i_history.HistoryInteractor
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigator
import javax.inject.Inject

@PerScreen
class PlayerDialogPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val sh: PlayerStateHolder,
    private val reducer: PlayerReducer,
    private val dialogNavigator: DialogNavigator,
    private val playerInteractor: PlayerInteractor,
    private val hitoryInteractor: HistoryInteractor
) : StatePresenter(dependency) {

    private val state: PlayerState get() = sh.value

    init {
        subscribeOnLastPlayedChanges()
        subscribeOnPlaybackStateChanges()
    }

    fun onStateBtnClick() {
        if (state.playbackState !is PlaybackState.Playing) {
            playerInteractor.play(state.episode)
        } else {
            playerInteractor.pause()
        }
    }

    private fun subscribeOnPlaybackStateChanges() {
        playerInteractor
            .observeAllStates()
            .subscribeIoDefault(reducer::onStateChange)
    }

    private fun subscribeOnLastPlayedChanges() {
        hitoryInteractor
            .observeLastPlayed()
            .subscribeDefault(reducer::onLastPlayedChange)
    }
}
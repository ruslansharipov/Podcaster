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
        when(state.playbackState){
            is PlaybackState.Buffering,
            is PlaybackState.Playing -> playerInteractor.pause()
            else -> playerInteractor.play(state.episode)
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
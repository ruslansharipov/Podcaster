package ru.sharipov.podcaster.f_player_dialog

import com.jakewharton.rxrelay2.PublishRelay
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerInteractor
import ru.sharipov.podcaster.base_feature.ui.extesions.show
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeFragmentRoute
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.i_history.HistoryInteractor
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigator
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.android.navigation.executor.NavigationCommandExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerScreen
class PlayerDialogPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val sh: PlayerStateHolder,
    private val reducer: PlayerReducer,
    private val navigationExecutor: NavigationCommandExecutor,
    private val playerInteractor: PlayerInteractor,
    private val historyInteractor: HistoryInteractor
) : StatePresenter(dependency) {

    companion object {
        private const val SEEK_DEBOUNCE_INTERVAL_MS = 300L
    }

    private val state: PlayerState
        get() = sh.value

    private val userSeekRelay = PublishRelay.create<Int>()

    override fun onFirstLoad() {
        subscribeOnLastPlayedChanges()
        subscribeOnPlaybackStateChanges()
        subscribeOnPositionChanges()
        subscribeOnBufferingChanges()
        subscribeOnSeekEvents()
    }

    fun onStateBtnClick() {
        when(state.playbackState){
            is PlaybackState.Buffering,
            is PlaybackState.Playing -> playerInteractor.pause()
            else -> {
                val episode = state.episode
                if (episode != null) {
                    playerInteractor.play(episode)
                }
            }
        }
    }

    fun onUserSeeks(progress: Int) {
        userSeekRelay.accept(progress)
    }

    fun onReplayClick() {
        onUserSeeks(state.position - 10)
    }

    fun onForwardClick() {
        onUserSeeks(state.position + 30)
    }

    fun onSubtitleClick() {
        val episode = state.episode
        if (episode != null) {
            navigationExecutor.show(EpisodeFragmentRoute(episode))
        }
    }

    private fun subscribeOnPlaybackStateChanges() {
        playerInteractor
            .observeAllStates()
            .subscribeIoDefault(reducer::onStateChange)
    }

    private fun subscribeOnLastPlayedChanges() {
        historyInteractor
            .observeLastPlayed()
            .subscribeDefault(reducer::onLastPlayedChange)
    }

    private fun subscribeOnPositionChanges() {
        historyInteractor
            .observePosition()
            .subscribeDefault(reducer::onPositionChange)
    }

    private fun subscribeOnBufferingChanges() {
        playerInteractor
            .observeBufferingPosition()
            .subscribeDefault(reducer::onBufferingPositionChange)
    }

    private fun subscribeOnSeekEvents() {
        userSeekRelay
            .doOnNext { reducer.onPositionChange(it) }
            .debounce(SEEK_DEBOUNCE_INTERVAL_MS, TimeUnit.MILLISECONDS)
            .subscribeDefault { playerInteractor.seek(it.toLong() * 1000) }
    }
}
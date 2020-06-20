package ru.sharipov.podcaster.f_episode

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerInteractor
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeFragmentRoute
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigator
import javax.inject.Inject

@PerScreen
class EpisodePresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val route: EpisodeFragmentRoute,
    private val dialogNavigator: DialogNavigator,
    private val episodeReducer: EpisodeReducer,
    private val episodeStateHolder: EpisodeStateHolder,
    private val playerInteractor: PlayerInteractor
): StatePresenter(dependency) {

    private val state: EpisodeState
        get() = episodeStateHolder.value

    override fun onFirstLoad() {
        super.onFirstLoad()
        subscibeOnPlaybackStateChanges()
    }

    fun onPlayBtnClick() {
        when(state.playbackState){
            is PlaybackState.Buffering,
            is PlaybackState.Playing -> playerInteractor.pause()
            else -> {
                playerInteractor.play(state.episode)
                dialogNavigator.dismiss(route)
            }
        }
    }

    fun onShareClick() {
        // todo
    }

    fun onFavoriteClick() {
        // todo
    }

    private fun subscibeOnPlaybackStateChanges() {
        val id = state.episode.id
        playerInteractor
            .observeState(id)
            .subscribeDefault(episodeReducer::onStateChange)
    }
}

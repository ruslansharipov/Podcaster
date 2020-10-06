package ru.sharipov.podcaster.f_episode

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerInteractor
import ru.sharipov.podcaster.base_feature.ui.extesions.start
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.ShareEpisodeRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.UrlRoute
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

@PerScreen
class EpisodePresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val route: EpisodeFragmentRoute,
    private val navigationExecutor: AppCommandExecutor,
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
            }
        }
    }

    fun onShareClick() {
        navigationExecutor.start(ShareEpisodeRoute(state.episode))
    }

    fun onFavoriteClick() {
        // todo
    }

    fun onLinkClick(url: String) {
        navigationExecutor.start(UrlRoute(url))
    }

    private fun subscibeOnPlaybackStateChanges() {
        val id = state.episode.id
        playerInteractor
            .observeState(id)
            .subscribeDefault(episodeReducer::onStateChange)
    }
}

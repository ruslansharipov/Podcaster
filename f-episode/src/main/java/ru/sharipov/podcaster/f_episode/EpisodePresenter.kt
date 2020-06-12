package ru.sharipov.podcaster.f_episode

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerInteractor
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class EpisodePresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val episodeReducer: EpisodeReducer,
    private val episodeStateHolder: EpisodeStateHolder,
    private val tabNavigator: TabFragmentNavigator,
    private val playerInteractor: PlayerInteractor
): StatePresenter(dependency) {

    init {
        val id = episodeStateHolder.value.episode.id
        playerInteractor
            .observeState(id)
            .subscribeDefault(episodeReducer::onStateChange)
    }

    private val state: EpisodeState
        get() = episodeStateHolder.value

    fun onBackPressed() {
        tabNavigator.onBackPressed()
    }

    fun onPlayBtnClick() {
        when(state.playbackState){
            is PlaybackState.Buffering,
            is PlaybackState.Playing -> playerInteractor.pause()
            else -> playerInteractor.play(state.episode)
        }
    }

}

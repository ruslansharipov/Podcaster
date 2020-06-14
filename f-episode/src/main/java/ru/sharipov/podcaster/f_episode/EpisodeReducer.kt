package ru.sharipov.podcaster.f_episode

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeFragmentRoute
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class EpisodeState(
    val episode: Episode,
    val playbackState: PlaybackState = PlaybackState.Idle
)

@PerScreen
class EpisodeStateHolder @Inject constructor(
    route: EpisodeFragmentRoute
) : State<EpisodeState>(EpisodeState(route.episode))

@PerScreen
class EpisodeReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: EpisodeStateHolder
) : StateReducer(dependency) {

    fun onStateChange(state: PlaybackState) {
        sh.emitNewState {
            copy(playbackState = state)
        }
    }
}

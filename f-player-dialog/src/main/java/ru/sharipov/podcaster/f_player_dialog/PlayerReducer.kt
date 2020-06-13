package ru.sharipov.podcaster.f_player_dialog

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class PlayerState(
    val episode: Episode = Episode(),
    val playbackState: PlaybackState = PlaybackState.Idle,
    val position: Int = 0,
    val bufferingPosition: Int = 0
)

@PerScreen
class PlayerStateHolder @Inject constructor() : State<PlayerState>(PlayerState())

@PerScreen
class PlayerReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: PlayerStateHolder
) : StateReducer(dependency) {

    fun onBufferingPositionChange(newBufferingPosition: Int){
        sh.emitNewState {
            copy(bufferingPosition = newBufferingPosition)
        }
    }

    fun onPositionChange(newPosition: Int) {
        sh.emitNewState {
            copy(position = newPosition)
        }
    }

    fun onLastPlayedChange(episode: Episode) {
        sh.emitNewState {
            copy(
                episode = episode
            )
        }
    }

    fun onStateChange(playbackState: PlaybackState) {
        sh.emitNewState {
            copy(
                playbackState = playbackState
            )
        }
    }
}
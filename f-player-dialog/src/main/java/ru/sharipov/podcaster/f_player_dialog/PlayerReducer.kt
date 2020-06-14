package ru.sharipov.podcaster.f_player_dialog

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.util.TimeFormatter
import ru.sharipov.podcaster.domain.EMPTY_STRING
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class PlayerState(
    val episode: Episode? = null,
    val playbackState: PlaybackState = PlaybackState.Idle,
    val position: Int = 0,
    val bufferingPosition: Int = 0,
    val positionUi: String = EMPTY_STRING,
    val remainsUi: String = EMPTY_STRING
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
            copy(
                position = newPosition,
                positionUi = TimeFormatter.fromSeconds(newPosition),
                remainsUi = "-${TimeFormatter.fromSeconds(episode?.duration?.minus(newPosition))}"
            )
        }
    }

    fun onLastPlayedChange(episode: Episode) {
        sh.emitNewState {
            copy(
                episode = episode,
                positionUi = TimeFormatter.fromSeconds(position),
                remainsUi = "-${TimeFormatter.fromSeconds(episode.duration - position)}"
            )
        }
    }

    fun onStateChange(playbackState: PlaybackState) {
        sh.emitNewState {
            copy(playbackState = playbackState)
        }
    }
}
package ru.sharipov.podcaster.f_player_dialog

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.domain.player.MediaState
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import javax.inject.Inject

data class PlayerState(
    val id: String? = EMPTY_STRING,
    val podcast: String? = null,
    val title: String? = null,
    val image: String? = null,
    val streamUrl: String? = null,
    val duration: Int? = null,
    val playbackState: PlaybackState = PlaybackState.Idle
)

@PerScreen
class PlayerStateHolder @Inject constructor() : State<PlayerState>(PlayerState())

@PerScreen
class PlayerReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: PlayerStateHolder
) : StateReducer(dependency) {

    fun onStateChange(playbackState: PlaybackState) {
        sh.emitNewState {
            copy(
                id = if (playbackState is MediaState) playbackState.media?.id else id,
                playbackState = playbackState
            )
        }
    }
}
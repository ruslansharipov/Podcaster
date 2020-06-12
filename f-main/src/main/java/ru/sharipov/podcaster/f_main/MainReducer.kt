package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.surfstudio.android.core.mvp.binding.rx.extensions.Optional
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.logger.Logger
import javax.inject.Inject

data class MainState(
    val currentTabType: MainTabType = MainTabType.EXPLORE,
    val playbackState: PlaybackState = PlaybackState.Idle,
    val lastPlayed: Optional<Episode> = Optional.empty(),
    val position: Int = 0
)

@PerScreen
class MainStateHolder @Inject constructor() : State<MainState>(MainState())

@PerScreen
class MainReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: MainStateHolder
) : StateReducer(dependency) {

    fun onPositionChange(newPosition: Int) {
        val lastPlayed = sh.value.lastPlayed.getOrNull()
        Logger.d(
            "newPosition: $newPosition, duration: ${lastPlayed?.duration}"
        )
        if (lastPlayed != null){
            sh.emitNewState {
                copy(position = newPosition)
            }
        }
    }

    fun onTabSelected(tabType: MainTabType) {
        sh.emitNewState {
            copy(currentTabType = tabType)
        }
    }

    fun onStateChange(state: PlaybackState) {
        sh.emitNewState {
            copy(
                playbackState = state
            )
        }
    }

    fun onLastPlayedChanged(lastPlayed: Episode) {
        sh.emitNewState {
            copy(
                lastPlayed = Optional.of(lastPlayed)
            )
        }
    }
}
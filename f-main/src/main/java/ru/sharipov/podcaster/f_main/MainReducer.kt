package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducer
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.data.AppInsets
import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainActivityRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainTabType
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.surfstudio.android.core.mvp.binding.rx.extensions.Optional
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

data class MainState(
    val currentTabType: MainTabType,
    val playbackState: PlaybackState = PlaybackState.Idle,
    val lastPlayed: Optional<Episode> = Optional.empty(),
    val position: Int = 0,
    val bufferingPosition: Int = 0,
    val insets: AppInsets = AppInsets()
)

@PerScreen
class MainStateHolder @Inject constructor(
    route: MainActivityRoute
) : State<MainState>(MainState(route.startTab))

@PerScreen
class MainReducer @Inject constructor(
    dependency: StateReducerDependency,
    private val sh: MainStateHolder
) : StateReducer(dependency) {

    fun onBufferingPositionChange(newBufferingPosition: Int){
        sh.emitNewState {
            copy(bufferingPosition = newBufferingPosition)
        }
    }

    fun onPositionChange(newPosition: Int) {
        val lastPlayed = sh.value.lastPlayed.getOrNull()
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
            copy(playbackState = state)
        }
    }

    fun onLastPlayedChanged(lastPlayed: Episode) {
        sh.emitNewState {
            copy(lastPlayed = Optional.of(lastPlayed))
        }
    }

    fun onInsetChange(insets: AppInsets) {
        sh.emitNewState {
            copy(insets = insets)
        }
    }
}
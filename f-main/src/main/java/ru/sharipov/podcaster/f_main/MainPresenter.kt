package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerInteractor
import ru.sharipov.podcaster.base_feature.ui.extesions.replace
import ru.sharipov.podcaster.base_feature.ui.extesions.show
import ru.sharipov.podcaster.base_feature.ui.navigation.*
import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainTabType
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.i_history.HistoryInteractor
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val stateHolder: MainStateHolder,
    private val mainReducer: MainReducer,
    private val navigationExecutor: AppCommandExecutor,
    private val playerInteractor: PlayerInteractor,
    private val historyInteractor: HistoryInteractor
) : StatePresenter(dependency) {

    private val mainState: MainState
        get() = stateHolder.value

    override fun onFirstLoad() {
        subscribeOnPlaybackState()
        subscribeOnLastPlayed()
        subscribeOnBufferingChanges()

        val tabType = mainState.currentTabType
        val route: FragmentRoute = createRouteForTab(tabType)
        navigationExecutor.replace(route)
    }

    fun onBottomTabClick(tabType: MainTabType) {
        val route: FragmentRoute = createRouteForTab(tabType)
        navigationExecutor.replace(route)
        mainReducer.onTabSelected(tabType)
    }

    fun onPlayerClick() {
        navigationExecutor.show(PlayerDialogRoute())
    }

    fun onPlayPauseClick() {
        val playbackState = mainState.playbackState
        val lastPlayed = mainState.lastPlayed.getOrNull()

        val isPlaying = playbackState is PlaybackState.Playing
        val isBuffering = playbackState is PlaybackState.Buffering
        when {
            isBuffering || isPlaying -> playerInteractor.pause()
            lastPlayed != null -> playerInteractor.play(lastPlayed)
        }
    }

    private fun subscribeOnPlaybackState() {
        playerInteractor
            .observeAllStates()
            .subscribeDefault(mainReducer::onStateChange)
    }

    private fun subscribeOnLastPlayed() {
        historyInteractor
            .observeLastPlayed()
            .subscribeIoDefault(mainReducer::onLastPlayedChanged)
    }

    private fun subscribeOnBufferingChanges() {
        playerInteractor
            .observeBufferingPosition()
            .subscribeIoDefault(mainReducer::onBufferingPositionChange)
    }

    private fun createRouteForTab(tabType: MainTabType): FragmentRoute {
        return when (tabType) {
            MainTabType.EXPLORE -> CuratedListFragmentRoute()
            MainTabType.FEED -> SubscriptionsFragmentRoute()
            MainTabType.PROFILE -> ActivityFragmentRoute()
        }
    }

}
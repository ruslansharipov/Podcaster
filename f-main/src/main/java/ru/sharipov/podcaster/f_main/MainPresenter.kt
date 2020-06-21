package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerInteractor
import ru.sharipov.podcaster.base_feature.ui.data.AppInsets
import ru.sharipov.podcaster.base_feature.ui.navigation.*
import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainTabType
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.i_history.HistoryInteractor
import ru.surfstudio.android.core.ui.navigation.fragment.route.FragmentRoute
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigator
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val stateHolder: MainStateHolder,
    private val mainReducer: MainReducer,
    private val tabNavigator: TabFragmentNavigator,
    private val dialogNavigator: DialogNavigator,
    private val playerInteractor: PlayerInteractor,
    private val historyInteractor: HistoryInteractor
) : StatePresenter(dependency) {

    private val mainState: MainState
        get() = stateHolder.value

    override fun onFirstLoad() {
        subscribeOnPlaybackState()
        subscribeOnLastPlayed()
        subscribeOnPositionChanges()
        subscribeOnBufferingChanges()

        val tabType = mainState.currentTabType
        val route: FragmentRoute = createRouteForTab(tabType)
        tabNavigator.open(route)
    }

    fun onBottomTabClick(tabType: MainTabType) {
        val route: FragmentRoute = createRouteForTab(tabType)
        tabNavigator.open(route)
        mainReducer.onTabSelected(tabType)
    }

    fun onPlayerClick() {
        dialogNavigator.show(PlayerDialogRoute())
    }

    fun onInsetsChange(insets: AppInsets) {
        Logger.d("$insets")
        mainReducer.onInsetChange(insets)
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
            .subscribeDefault(mainReducer::onLastPlayedChanged)
    }

    private fun subscribeOnPositionChanges() {
        historyInteractor
            .observePosition()
            .subscribeDefault (mainReducer::onPositionChange)
    }

    private fun subscribeOnBufferingChanges() {
        playerInteractor
            .observeBufferingPosition()
            .subscribeDefault(mainReducer::onBufferingPositionChange)
    }

    private fun createRouteForTab(tabType: MainTabType): FragmentRoute {
        return when (tabType) {
            MainTabType.EXPLORE -> CuratedListFragmentRoute()
            MainTabType.FEED -> SubscriptionsFragmentRoute()
            MainTabType.PROFILE -> ProfileFragmentRoute()
        }
    }

}
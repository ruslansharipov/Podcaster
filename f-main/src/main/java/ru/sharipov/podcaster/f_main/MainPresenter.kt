package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerInteractor
import ru.sharipov.podcaster.base_feature.ui.navigation.*
import ru.sharipov.podcaster.i_history.HistoryInteractor
import ru.surfstudio.android.core.ui.navigation.fragment.route.FragmentRoute
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
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

    init {
        subscribeOnPlaybackState()
        subscribeOnLastPlayed()
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

    override fun onFirstLoad() {
        val tabType = stateHolder.value.currentTabType
        val route: FragmentRoute = createRouteForTab(tabType)
        tabNavigator.open(route)
    }

    fun onBottomTabClick(tabType: MainTabType) {
        val route: FragmentRoute = createRouteForTab(tabType)
        tabNavigator.open(route)// TODO раскоментить когда появятся роуты
        mainReducer.onTabSelected(tabType)
    }

    fun onPlayerClick() {
        dialogNavigator.show(PlayerDialogRoute())
    }

    private fun createRouteForTab(tabType: MainTabType): FragmentRoute {
        return when (tabType) {
            MainTabType.EXPLORE -> CuratedListFragmentRoute()
            MainTabType.SEARCH -> SearchFragmentRoute()
            MainTabType.PROFILE -> SubscriptionsFragmentRoute()
        }
    }

}
package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.*
import ru.surfstudio.android.core.ui.navigation.fragment.route.FragmentRoute
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val stateHolder: MainStateHolder,
    private val mainReducer: MainReducer,
    private val tabNavigator: TabFragmentNavigator
) : StatePresenter(dependency) {

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

    private fun createRouteForTab(tabType: MainTabType): FragmentRoute {
        return when (tabType) {
            MainTabType.EXPLORE -> CuratedListFragmentRoute()
            MainTabType.SEARCH -> SearchFragmentRoute()
            MainTabType.PROFILE -> SubscriptionsFragmentRoute()
        }
    }

}
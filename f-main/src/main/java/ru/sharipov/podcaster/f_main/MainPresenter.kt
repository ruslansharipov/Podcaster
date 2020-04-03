package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.ExploreFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.FeedFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.PlaylistFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.SearchFragmentRoute
import ru.surfstudio.android.core.ui.navigation.fragment.route.FragmentRoute
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor(
    private val mainReducer: MainReducer,
    private val tabNavigator: TabFragmentNavigator,
    dependency: StatePresenterDependency
) : StatePresenter(dependency) {

    fun onBottomTabClick(tabType: MainTabType) {
        val route: FragmentRoute = when(tabType){
            MainTabType.EXPLORE -> ExploreFragmentRoute()
            MainTabType.SEARCH -> SearchFragmentRoute()
            MainTabType.FEED -> FeedFragmentRoute()
            MainTabType.PLAYLIST -> PlaylistFragmentRoute()
        }
        tabNavigator.open(route)// TODO раскоментить когда появятся роуты
        mainReducer.onTabSelected(tabType)
    }

}
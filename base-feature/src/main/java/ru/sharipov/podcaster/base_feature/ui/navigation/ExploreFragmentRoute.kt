package ru.sharipov.podcaster.base_feature.ui.navigation

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.surfstudio.android.navigation.route.tab.TabRootRoute

class ExploreFragmentRoute: FragmentRoute(), TabRootRoute {

    override fun getScreenClassPath(): String {
        return "ru.sharipov.podcaster.f_explore.explore.ExploreFragmentView"
    }
}
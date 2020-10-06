package ru.sharipov.podcaster.base_feature.ui.navigation

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.surfstudio.android.navigation.route.tab.TabHeadRoute

class CuratedListFragmentRoute: FragmentRoute(), TabHeadRoute {

    override fun getScreenClassPath(): String {
        return "ru.sharipov.podcast.f_curated_list.CuratedListFragmentView"
    }
}
package ru.sharipov.podcaster.base_feature.ui.navigation

import ru.surfstudio.android.core.ui.navigation.feature.route.feature.FragmentCrossFeatureRoute
import ru.surfstudio.android.core.ui.navigation.fragment.route.RootFragmentRoute

class CuratedListFragmentRoute: FragmentCrossFeatureRoute(), RootFragmentRoute {

    override fun targetClassPath(): String {
        return "ru.sharipov.podcast.f_curated_list.CuratedListFragmentView"
    }
}
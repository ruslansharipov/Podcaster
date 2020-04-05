package ru.sharipov.podcaster.base_feature.ui.navigation

import ru.surfstudio.android.core.ui.navigation.feature.route.feature.FragmentCrossFeatureRoute

class CuratedListFragmentRoute: FragmentCrossFeatureRoute() {

    override fun targetClassPath(): String {
        return "ru.sharipov.podcast.f_curated_list.CuratedListFragmentView"
    }
}
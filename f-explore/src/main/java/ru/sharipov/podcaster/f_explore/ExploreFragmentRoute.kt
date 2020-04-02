package ru.sharipov.podcaster.f_explore

import ru.surfstudio.android.core.ui.navigation.feature.route.feature.FragmentCrossFeatureRoute

class ExploreFragmentRoute: FragmentCrossFeatureRoute() {

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_explore.ExploreFragmentView"
    }
}

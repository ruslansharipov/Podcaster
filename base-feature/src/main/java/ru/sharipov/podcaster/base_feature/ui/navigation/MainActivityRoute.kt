package ru.sharipov.podcaster.base_feature.ui.navigation

import ru.surfstudio.android.core.ui.navigation.feature.route.feature.ActivityCrossFeatureRoute

class MainActivityRoute : ActivityCrossFeatureRoute() {

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_main.MainActivityView"
    }
}
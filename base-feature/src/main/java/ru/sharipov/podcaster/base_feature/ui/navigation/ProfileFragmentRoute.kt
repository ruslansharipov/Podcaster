package ru.sharipov.podcaster.base_feature.ui.navigation

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.surfstudio.android.navigation.route.tab.TabRootRoute

class ProfileFragmentRoute: FragmentRoute(), TabRootRoute {

    override fun getScreenClassPath(): String {
        return "ru.sharipov.podcaster.f_profile.ProfileFragmentView"
    }
}
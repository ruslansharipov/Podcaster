package ru.sharipov.podcaster.base_feature.ui.navigation.main

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.activity.ActivityRoute

class MainActivityRoute(
    val startTab: MainTabType = MainTabType.PROFILE
) : ActivityRoute() {

    constructor(intent: Intent) : this(intent.getParcelableExtra<MainTabType>(Route.EXTRA_FIRST))

    override fun createIntent(context: Context): Intent {
        return super
            .createIntent(context)
            .putExtra(Route.EXTRA_FIRST, startTab as Parcelable)
    }

    override fun getScreenClassPath(): String? {
        return "ru.sharipov.podcaster.f_main.MainActivityView"
    }
}
package ru.sharipov.podcaster.base_feature.ui.navigation.main

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import ru.surfstudio.android.core.ui.navigation.Route
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.ActivityCrossFeatureWithParamsRoute

class MainActivityRoute(
    val startTab: MainTabType
) : ActivityCrossFeatureWithParamsRoute() {

    constructor(intent: Intent): this(intent.getParcelableExtra<MainTabType>(Route.EXTRA_FIRST))

    override fun prepareIntent(context: Context): Intent? {
        return super.prepareIntent(context)
            ?.putExtra(Route.EXTRA_FIRST, startTab as Parcelable)
    }

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_main.MainActivityView"
    }
}
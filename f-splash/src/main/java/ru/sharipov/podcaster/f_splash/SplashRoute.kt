package ru.sharipov.podcaster.f_splash

import android.content.Context
import android.content.Intent
import ru.surfstudio.android.core.ui.navigation.activity.route.ActivityRoute

class SplashRoute : ActivityRoute() {

    override fun prepareIntent(context: Context) = Intent(context, SplashActivityView::class.java)
}

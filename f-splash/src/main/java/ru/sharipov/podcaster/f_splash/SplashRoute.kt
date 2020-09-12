package ru.sharipov.podcaster.f_splash

import android.content.Context
import android.content.Intent
import ru.surfstudio.android.navigation.route.activity.ActivityRoute

class SplashRoute : ActivityRoute() {

    override fun createIntent(context: Context) = Intent(context, SplashActivityView::class.java)
}

package ru.sharipov.podcaster.base_feature.ui.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.surfstudio.android.navigation.route.activity.ActivityRoute

/**
 * Opens [url] in an appropriate application. It can be browser or
 * another app if it can open uri, created with given [url]
 *
 * @param url link to open
 */
class UrlRoute(private val url: String): ActivityRoute() {

    override fun createIntent(context: Context): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }
}
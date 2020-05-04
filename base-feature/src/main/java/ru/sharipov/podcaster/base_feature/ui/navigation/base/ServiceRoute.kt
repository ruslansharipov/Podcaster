package ru.sharipov.podcaster.base_feature.ui.navigation.base

import android.content.Context
import android.content.Intent
import ru.surfstudio.android.core.ui.navigation.Route

abstract class ServiceRoute: Route {

    /**
     * Prepared Intent for calling the target Service and passing data.
     *
     * @param context context
     * @return prepared Intent
     */
    abstract fun prepareIntent(context: Context?): Intent?
}
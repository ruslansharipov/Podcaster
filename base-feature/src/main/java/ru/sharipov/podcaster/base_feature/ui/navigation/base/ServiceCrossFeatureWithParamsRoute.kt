package ru.sharipov.podcaster.base_feature.ui.navigation.base

import android.content.Context
import android.content.Intent
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureRoute
import ru.surfstudio.android.logger.Logger

abstract class ServiceCrossFeatureWithParamsRoute: ServiceRoute(), CrossFeatureRoute {

    abstract override fun targetClassPath(): String

    override fun prepareIntent(context: Context?): Intent? {
        try {
            return Intent(context, Class.forName(targetClassPath()))
        } catch (e: ClassNotFoundException) {
            Logger.e("Service with the following classpath was not found in the current " +
                    "application: ${targetClassPath()}. If this service is the part of Dynamic Feature, " +
                    "please check if this Dynamic Feature is downloaded and installed on the device" +
                    "successfully.")
        }
        return null
    }
}
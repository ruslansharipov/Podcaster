package ru.sharipov.podcaster.base_feature.ui.navigation.base

import android.content.Context
import android.content.Intent
import ru.surfstudio.android.logger.Logger

abstract class ServiceWithParamsRoute: ServiceRoute() {

    abstract fun getServiceClassPath(): String

    override fun prepareIntent(context: Context?): Intent {
        return try {
            Intent(context, Class.forName(getServiceClassPath()))
        } catch (e: ClassNotFoundException) {
            Logger.e("Service with the following classpath was not found in the current " +
                    "application: ${getServiceClassPath()}. If this service is the part of Dynamic Feature, " +
                    "please check if this Dynamic Feature is downloaded and installed on the device" +
                    "successfully.")
            throw e
        }
    }
}
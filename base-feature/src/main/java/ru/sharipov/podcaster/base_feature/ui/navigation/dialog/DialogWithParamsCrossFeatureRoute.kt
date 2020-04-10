package ru.sharipov.podcaster.base_feature.ui.navigation.dialog

import androidx.fragment.app.DialogFragment
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureRoute
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.mvp.dialog.navigation.route.DialogWithParamsRoute

/**
 * CrossFeature роут для диалога с параметрами
 */
abstract class DialogWithParamsCrossFeatureRoute : DialogWithParamsRoute(), CrossFeatureRoute {

    @Suppress("UNCHECKED_CAST")
    override fun getFragmentClass(): Class<out DialogFragment>? {
        try {
            return Class.forName(targetClassPath()) as? Class<out DialogFragment> ?: return null
        } catch (e: ClassNotFoundException) {
            Logger.e("Fragment with the following classpath was not found in the current " +
                    "application: ${targetClassPath()}. If this fragment is the part of Dynamic Feature, " +
                    "please check if this Dynamic Feature is downloaded and installed on the device" +
                    "successfully.")
        }
        return null
    }
}

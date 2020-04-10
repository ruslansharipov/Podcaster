package ru.sharipov.podcaster.base_feature.ui.navigation

import ru.sharipov.podcaster.base_feature.ui.navigation.dialog.DialogCrossFeatureRoute

class RegionDialogRoute : DialogCrossFeatureRoute() {

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_region.RegionDialogView"
    }
}
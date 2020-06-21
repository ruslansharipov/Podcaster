package ru.sharipov.podcaster.base_feature.ui.navigation

import ru.sharipov.podcaster.base_feature.ui.navigation.dialog.DialogCrossFeatureRoute

class SearchDialogRoute: DialogCrossFeatureRoute() {

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_search.SearchDialogView"
    }
}
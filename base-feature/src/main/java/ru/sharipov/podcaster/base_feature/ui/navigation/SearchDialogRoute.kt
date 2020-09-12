package ru.sharipov.podcaster.base_feature.ui.navigation

import ru.surfstudio.android.navigation.route.dialog.DialogRoute

class SearchDialogRoute: DialogRoute() {

    override fun getScreenClassPath(): String {
        return "ru.sharipov.podcaster.f_search.SearchDialogView"
    }
}
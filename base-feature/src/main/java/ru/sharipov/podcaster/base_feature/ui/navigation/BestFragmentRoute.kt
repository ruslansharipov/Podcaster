package ru.sharipov.podcaster.base_feature.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.sharipov.podcaster.domain.Genre
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class BestFragmentRoute(
    val genre: Genre
): FragmentRoute() {

    constructor(bundle: Bundle): this(
        bundle.getParcelable<Genre>(Route.EXTRA_FIRST)
    )

    override fun prepareData(): Bundle {
        return bundleOf(
            Route.EXTRA_FIRST to genre
        )
    }

    override fun getScreenClassPath(): String? {
        return "ru.sharipov.podcaster.f_best.BestFragmentView"
    }
}
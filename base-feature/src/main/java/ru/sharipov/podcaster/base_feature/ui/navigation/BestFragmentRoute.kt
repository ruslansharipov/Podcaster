package ru.sharipov.podcaster.base_feature.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.Region
import ru.surfstudio.android.core.ui.navigation.Route
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.FragmentCrossFeatureWithParamsRoute

class BestFragmentRoute(
    val genre: Genre,
    val region: Region
): FragmentCrossFeatureWithParamsRoute() {

    constructor(bundle: Bundle): this(
        bundle.getParcelable<Genre>(Route.EXTRA_FIRST),
        bundle.getParcelable<Region>(Route.EXTRA_SECOND)
    )

    override fun prepareBundle(): Bundle {
        return bundleOf(
            Route.EXTRA_FIRST to genre,
            Route.EXTRA_SECOND to region
        )
    }

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_best.BestFragmentView"
    }
}
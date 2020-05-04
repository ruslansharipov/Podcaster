package ru.sharipov.podcaster.base_feature.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.core.ui.navigation.Route
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.FragmentCrossFeatureWithParamsRoute

class EpisodeFragmentRoute(
    val podcastTitle: String,
    val episode: Episode,
    val dateFormatted: String
) : FragmentCrossFeatureWithParamsRoute() {

    constructor(bundle: Bundle): this(
        bundle.getString(Route.EXTRA_FIRST),
        bundle.getParcelable<Episode>(Route.EXTRA_SECOND),
        bundle.getString(Route.EXTRA_THIRD)
    )

    override fun prepareBundle(): Bundle {
        return bundleOf(
            Route.EXTRA_FIRST to podcastTitle,
            Route.EXTRA_SECOND to episode,
            Route.EXTRA_THIRD to dateFormatted
        )
    }

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_episode.EpisodeFragmentView"
    }
}
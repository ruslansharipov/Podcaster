package ru.sharipov.podcaster.base_feature.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.sharipov.podcaster.domain.PodcastFull
import ru.surfstudio.android.core.ui.navigation.Route
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.FragmentCrossFeatureWithParamsRoute

class PodcastFragmentRoute(
    val podcast: PodcastFull
) : FragmentCrossFeatureWithParamsRoute() {

    companion object {
        private fun extractFrom(bundle: Bundle?): PodcastFull =
            bundle?.getParcelable<PodcastFull>(Route.EXTRA_FIRST) ?: PodcastFull()
    }

    constructor(bundle: Bundle?) : this(extractFrom(bundle))

    override fun prepareBundle(): Bundle = bundleOf(Route.EXTRA_FIRST to podcast)

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_podcast.PodcastFragmentView"
    }
}
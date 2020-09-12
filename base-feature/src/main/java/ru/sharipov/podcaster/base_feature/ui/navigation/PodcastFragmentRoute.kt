package ru.sharipov.podcaster.base_feature.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.sharipov.podcaster.domain.PodcastFull
import ru.surfstudio.android.core.ui.navigation.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute
import ru.surfstudio.android.navigation.route.tab.TabRoute

class PodcastFragmentRoute(
    val podcast: PodcastFull
) : FragmentRoute(), TabRoute {

    companion object {
        private fun extractFrom(bundle: Bundle?): PodcastFull =
            bundle?.getParcelable<PodcastFull>(Route.EXTRA_FIRST) ?: PodcastFull()
    }

    constructor(bundle: Bundle?) : this(extractFrom(bundle))

    override fun prepareData(): Bundle = bundleOf(Route.EXTRA_FIRST to podcast)

    override fun getScreenClassPath(): String {
        return "ru.sharipov.podcaster.f_podcast.PodcastFragmentView"
    }
}
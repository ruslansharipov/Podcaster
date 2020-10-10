package ru.sharipov.podcaster.base_feature.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.sharipov.podcaster.domain.Subscription
import ru.surfstudio.android.core.ui.navigation.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class PodcastFragmentRoute(
    val podcast: Subscription
) : FragmentRoute() {

    constructor(bundle: Bundle?) : this(bundle?.getSerializable(Route.EXTRA_FIRST) as Subscription)

    override fun prepareData(): Bundle = bundleOf(Route.EXTRA_FIRST to podcast)

    override fun getScreenClassPath(): String {
        return "ru.sharipov.podcaster.f_podcast.PodcastFragmentView"
    }
}
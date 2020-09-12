package ru.sharipov.podcaster.base_feature.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.sharipov.podcaster.domain.EMPTY_STRING
import ru.surfstudio.android.core.ui.navigation.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class EpisodesFragmentRoute(
    val id: String
) : FragmentRoute() {

    constructor(bundle: Bundle?) : this(bundle?.getString(Route.EXTRA_FIRST) ?: EMPTY_STRING)

    override fun prepareData(): Bundle = bundleOf(Route.EXTRA_FIRST to id)

    override fun getScreenClassPath(): String {
        return "ru.sharipov.podcaster.f_podcast_episodes.EpisodesFragmentView"
    }
}
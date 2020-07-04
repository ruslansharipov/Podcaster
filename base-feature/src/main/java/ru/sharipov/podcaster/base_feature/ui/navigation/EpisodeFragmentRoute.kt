package ru.sharipov.podcaster.base_feature.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.core.ui.navigation.Route
import ru.surfstudio.android.navigation.route.dialog.DialogRoute

class EpisodeFragmentRoute(
    val episode: Episode
) : DialogRoute() {

    constructor(bundle: Bundle): this(bundle.getParcelable<Episode>(Route.EXTRA_FIRST))

    override fun prepareData(): Bundle {
        return bundleOf(Route.EXTRA_FIRST to episode)
    }

    override fun getScreenClassPath(): String? {
        return "ru.sharipov.podcaster.f_episode.EpisodeFragmentView"
    }
}
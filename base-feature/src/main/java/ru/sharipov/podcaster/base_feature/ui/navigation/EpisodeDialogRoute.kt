package ru.sharipov.podcaster.base_feature.ui.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.sharipov.podcaster.base_feature.ui.navigation.dialog.DialogWithParamsCrossFeatureRoute
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.core.ui.navigation.Route

class EpisodeDialogRoute(
    val episode: Episode
) : DialogWithParamsCrossFeatureRoute() {

    constructor(bundle: Bundle): this(bundle.getParcelable<Episode>(Route.EXTRA_FIRST))

    override fun prepareBundle(): Bundle {
        return bundleOf(Route.EXTRA_FIRST to episode)
    }

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_episode.EpisodeDialogView"
    }
}
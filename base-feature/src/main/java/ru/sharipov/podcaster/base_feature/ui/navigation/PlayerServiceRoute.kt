package ru.sharipov.podcaster.base_feature.ui.navigation

import android.content.Context
import android.content.Intent
import ru.sharipov.podcaster.base_feature.ui.navigation.base.ServiceCrossFeatureWithParamsRoute
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.surfstudio.android.core.ui.navigation.Route

class PlayerServiceRoute(
    val playerAction: PlayerAction
): ServiceCrossFeatureWithParamsRoute() {

    constructor(intent: Intent): this(
        intent.getParcelableExtra<PlayerAction>(Route.EXTRA_FIRST)
    )

    override fun prepareIntent(context: Context?): Intent {
        return super
            .prepareIntent(context)
            .putExtra(Route.EXTRA_FIRST, playerAction)
    }

    override fun targetClassPath(): String {
        return "ru.sharipov.podcaster.f_player.service.PlayerService"
    }
}
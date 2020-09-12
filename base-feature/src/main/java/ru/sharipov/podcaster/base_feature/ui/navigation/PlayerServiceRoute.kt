package ru.sharipov.podcaster.base_feature.ui.navigation

import android.content.Context
import android.content.Intent
import ru.sharipov.podcaster.base_feature.ui.navigation.base.ServiceWithParamsRoute
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.surfstudio.android.core.ui.navigation.Route

class PlayerServiceRoute(
    val playerAction: PlayerAction
): ServiceWithParamsRoute() {

    constructor(intent: Intent): this(
        intent.getParcelableExtra<PlayerAction>(Route.EXTRA_FIRST)
    )

    override fun prepareIntent(context: Context?): Intent {
        return super
            .prepareIntent(context)
            .putExtra(Route.EXTRA_FIRST, playerAction)
    }

    override fun getServiceClassPath(): String {
        return "ru.sharipov.podcaster.f_player.service.PlayerService"
    }
}
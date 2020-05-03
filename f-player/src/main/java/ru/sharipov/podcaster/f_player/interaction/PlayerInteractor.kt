package ru.sharipov.podcaster.f_player.interaction

import android.content.Context
import android.content.Intent
import ru.sharipov.podcaster.base_feature.ui.navigation.MainActivityRoute
import ru.sharipov.podcaster.f_player.service.PlayerService
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerServiceBus
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.domain.player.Media
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class PlayerInteractor @Inject constructor(
    private val serviceBus: PlayerServiceBus,
    private val context: Context
) {

    fun start() {
        context.startService(Intent(context, PlayerService::class.java).apply {
            putExtra(PlayerService.ARGS, MainActivityRoute().prepareIntent(context))
        })
    }

    fun play(media: Media) {
        serviceBus.emitAction(PlayerAction.Add(media))
    }

    fun pause() {
        serviceBus.emitAction(PlayerAction.Pause)
    }

    fun seek(position: Long) {
        serviceBus.emitAction(PlayerAction.Seek(position))
    }

    fun stop() {
        serviceBus.emitAction(PlayerAction.Stop)
    }
}
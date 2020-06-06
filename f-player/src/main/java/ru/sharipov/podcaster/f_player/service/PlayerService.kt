package ru.sharipov.podcaster.f_player.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.sharipov.podcaster.base_feature.application.app.di.AppInjector
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerServiceBus
import ru.sharipov.podcaster.base_feature.ui.navigation.PlayerServiceRoute
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.f_player.service.di.PlayerModule
import ru.sharipov.podcaster.f_player.media.MediaManager
import ru.sharipov.podcaster.f_player.service.di.DaggerPlayerServiceComponent
import ru.surfstudio.android.core.ui.navigation.Route
import javax.inject.Inject

class PlayerService : Service() {

    companion object {
        const val ACTION_PAUSE = "ru.sharipov.podcaster.pause"
        const val ACTION_PLAY  = "ru.sharipov.podcaster.start"
        const val ACTION_PREV  = "ru.sharipov.podcaster.previous"
        const val ACTION_NEXT  = "ru.sharipov.podcaster.next"
        const val ACTION_STOP  = "ru.sharipov.podcaster.stop"
        const val EXTRA_INTENT = "ru.sharipov.podcaster.intent"
    }
    @Inject
    lateinit var mediaManager: MediaManager
    @Inject
    lateinit var playerServiceBus: PlayerServiceBus

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        DaggerPlayerServiceComponent.builder()
            .appComponent(AppInjector.appComponent)
            .playerModule(PlayerModule(this))
            .build()
            .inject(this)
    }

    override fun onStartCommand(startIntent: Intent?, flags: Int, startId: Int): Int {
        if (startIntent != null) {
            if (startIntent.hasExtra(Route.EXTRA_FIRST)) {
                val route = PlayerServiceRoute(startIntent)
                val playerAction = route.playerAction
                mediaManager.onNewAction(playerAction)
            }
            val extras = startIntent.extras
            val action = startIntent.action
            when {
                extras != null -> mediaManager.onNewIntent(
                    extras.getParcelable(Route.EXTRA_SECOND)
                )
                action != null -> executeTask(action)
            }
            //MediaButtonReceiver.handleIntent(mediaSession, startIntent)
            // TODO
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mediaManager.onDestroy()
        stopForeground(true)
        super.onDestroy()
    }

    private fun executeTask(action: String) {
        when (action) {
            ACTION_PLAY -> playerServiceBus.emitAction(PlayerAction.Resume)
            ACTION_PREV -> playerServiceBus.emitAction(PlayerAction.Previous)
            ACTION_NEXT -> playerServiceBus.emitAction(PlayerAction.Next)
            ACTION_PAUSE -> playerServiceBus.emitAction(PlayerAction.Pause)
            ACTION_STOP -> playerServiceBus.emitAction(PlayerAction.Stop)
        }
    }
}
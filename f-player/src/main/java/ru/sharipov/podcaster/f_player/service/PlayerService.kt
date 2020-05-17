package ru.sharipov.podcaster.f_player.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import ru.sharipov.podcaster.base_feature.application.app.di.AppInjector
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerServiceBus
import ru.sharipov.podcaster.base_feature.ui.navigation.PlayerServiceRoute
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.domain.player.PlayerAction
import ru.sharipov.podcaster.f_player.notification.AppNotificationManager
import ru.sharipov.podcaster.f_player.playback.PlaybackInterface
import ru.sharipov.podcaster.f_player.service.di.PlayerModule
import ru.sharipov.podcaster.f_player.media.MediaManager
import ru.sharipov.podcaster.f_player.service.di.DaggerPlayerServiceComponent
import ru.surfstudio.android.core.ui.navigation.Route
import javax.inject.Inject

class PlayerService : Service(), PlaybackInterface.ServiceCallback {

    companion object {
        const val ACTION_PAUSE = "ru.sharipov.podcaster.pause"
        const val ACTION_PLAY  = "ru.sharipov.podcaster.start"
        const val ACTION_PREV  = "ru.sharipov.podcaster.previous"
        const val ACTION_NEXT  = "ru.sharipov.podcaster.next"
        const val ACTION_STOP  = "ru.sharipov.podcaster.stop"
        const val EXTRA_INTENT = "ru.sharipov.podcaster.intent"
    }

    @Inject
    lateinit var notificationManager: AppNotificationManager
    @Inject
    lateinit var mediaSession: MediaSessionCompat
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
        mediaManager.subscribePosition()
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
                extras != null -> notificationManager.setIntent(extras.getParcelable(
                    Route.EXTRA_SECOND
                ))
                action != null -> executeTask(action)
            }
            //MediaButtonReceiver.handleIntent(mediaSession, startIntent)
            // TODO
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mediaSession.release()
        mediaManager.unSubscribe()
        stopForeground(true)
        super.onDestroy()
    }

    override fun onPlaybackStateChanged(state: PlaybackState, position: Long) {
        setSessionState(state, position)
        notificationManager.updateState(state)
    }

    override fun onPlaybackMediaChanged(media: Episode?) {
        mediaSession.setMetadata(getMetadata(media))
        notificationManager.updateMedia(media)
    }

    private fun setSessionState(state: PlaybackState, position: Long) {
        val stateCompat = PlaybackStateCompat.Builder()
            .setActions(getActions(state))
            .setState(
                when (state) {
                    is PlaybackState.Playing -> PlaybackStateCompat.STATE_PLAYING
                    is PlaybackState.Buffering -> PlaybackStateCompat.STATE_BUFFERING
                    is PlaybackState.Paused -> PlaybackStateCompat.STATE_PAUSED
                    is PlaybackState.Completed -> PlaybackStateCompat.STATE_PAUSED
                    is PlaybackState.Stopped -> PlaybackStateCompat.STATE_STOPPED
                    else -> PlaybackStateCompat.STATE_NONE
                }, position, 1.0f, SystemClock.elapsedRealtime()
            )
            .build()

        mediaSession.isActive = state is PlaybackState.Playing || state is PlaybackState.Buffering
        mediaSession.setPlaybackState(stateCompat)
    }

    private fun getActions(state: PlaybackState): Long {
        var actions = PlaybackStateCompat.ACTION_PLAY_PAUSE or
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                PlaybackStateCompat.ACTION_SET_REPEAT_MODE or
                PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE
        actions = if (state is PlaybackState.Playing || state is PlaybackState.Buffering) {
            actions or PlaybackStateCompat.ACTION_PAUSE
        } else {
            actions or PlaybackStateCompat.ACTION_PLAY
        }
        return actions
    }

    private fun getMetadata(media: Episode?): MediaMetadataCompat {
        return MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_ART_URI, media?.image)
            .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, media?.title)
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, media?.podcastTitle)
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, media?.streamUrl)
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, media?.id)
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, media?.duration?.toLong() ?: 0)
            .build()
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
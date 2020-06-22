package ru.sharipov.podcaster.f_player.notification

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.f_player.service.PlayerService

class AppNotificationManager(
    private val service: Service,
    private val token: MediaSessionCompat.Token,
    private val notificationManager: NotificationManagerCompat
) {

    private companion object {
        const val NOTIFICATION_ID = 100

        const val PLAYER_PENDING_INTENT_ID = 10
        const val PAUSE_PENDING_INTENT_ID = 20
        const val PLAY_PENDING_INTENT_ID = 30
        const val PLAY_NEXT_PENDING_INTENT_ID = 40
        const val PLAY_PREV_PENDING_INTENT_ID = 50
        const val STOP_PENDING_INTENT_ID = 60
    }

    private val channelId: String = service.getString(R.string.app_name)

    private val handler = Handler()
    private var state: PlaybackState = PlaybackState.Idle

    init {
        createNotificationChannels()
    }

    fun updateMedia(media: Episode?) {
        updateNotification(media)
    }

    fun updateState(state: PlaybackState) {
        this.state = state
        when (state) {
            is PlaybackState.Buffering -> updateNotification(state.media)
            is PlaybackState.Playing -> updateNotification(state.media)
            is PlaybackState.Paused -> pauseNotification(state.media)
            is PlaybackState.Completed -> pauseNotification(state.media)
            else -> stopNotification()
        }
    }

    private fun pauseNotification(media: Episode?) {
        updateNotification(media)
        handler.postDelayed({
            service.stopForeground(false)
        }, 100)
    }

    private fun stopNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
        service.stopSelf()
    }

    private fun createPauseAction(): NotificationCompat.Action {
        val pendingIntent = getPendingIntent(PAUSE_PENDING_INTENT_ID, PlayerService.ACTION_PAUSE)
        return NotificationCompat.Action(R.drawable.ic_pause, "Pause", pendingIntent)
    }

    private fun createForwardAction(): NotificationCompat.Action {
        val pendingIntent =
            getPendingIntent(PLAY_NEXT_PENDING_INTENT_ID, PlayerService.ACTION_FORWARD)
        return NotificationCompat.Action(R.drawable.ic_forward_30, "Forward", pendingIntent)
    }

    private fun createReplayAction(): NotificationCompat.Action {
        val pendingIntent =
            getPendingIntent(PLAY_PREV_PENDING_INTENT_ID, PlayerService.ACTION_REPLAY)
        return NotificationCompat.Action(R.drawable.ic_replay_10, "Replay", pendingIntent)
    }

    private fun createPlayAction(): NotificationCompat.Action {
        val pendingIntent = getPendingIntent(PLAY_PENDING_INTENT_ID, PlayerService.ACTION_PLAY)
        return NotificationCompat.Action(R.drawable.ic_play, "Play", pendingIntent)
    }

    private fun createStopIntent(): PendingIntent {
        return getPendingIntent(STOP_PENDING_INTENT_ID, PlayerService.ACTION_STOP)
    }

    private fun getPendingIntent(intentId: Int, action: String): PendingIntent {
        val prevIntent = Intent(service, PlayerService::class.java)
        prevIntent.action = action

        return PendingIntent.getService(
            service,
            intentId,
            prevIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun updateNotification(media: Episode?) {
        showNotification(null, media)

        Glide.with(service)
            .asBitmap()
            .load(media?.image)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    showNotification(resource, media)
                }
            })
    }

    private fun showNotification(
        bitmap: Bitmap?,
        media: Episode?
    ) {
        if (media == null || state == PlaybackState.Idle) {
            return
        }
        val builder = createBuilder()

        val notification = builder
            .setStyle(
            androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(0, 1, 2)
        )
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_play)
            .setContentTitle(media.title)
            .setContentText(media.podcastTitle)
            .setDeleteIntent(createStopIntent())
            .addAction(createReplayAction())
            .addPlaybackStateAction(state)
            .addAction(createForwardAction())
            .setLargeIcon(bitmap)
            .build()
        service.startForeground(NOTIFICATION_ID, notification)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun NotificationCompat.Builder.addPlaybackStateAction(
        state: PlaybackState
    ): NotificationCompat.Builder = apply {
        val (action, isOngoing) = if (state is PlaybackState.Paused || state is PlaybackState.Completed) {
            createPlayAction() to false
        } else {
            createPauseAction() to true
        }
        addAction(action)
        setOngoing(isOngoing)
    }

    private fun createBuilder(): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(service, channelId)
        } else {
            NotificationCompat.Builder(service)
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelPrimary = NotificationChannel(
                channelId,
                channelId,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channelPrimary.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channelPrimary.setSound(null, null)
            channelPrimary.enableVibration(false)
            notificationManager.createNotificationChannel(channelPrimary)
        }
    }
}
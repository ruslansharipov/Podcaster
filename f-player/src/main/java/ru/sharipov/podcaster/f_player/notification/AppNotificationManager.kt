package ru.sharipov.podcaster.f_player.notification

import android.app.*
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import ru.sharipov.podcaster.domain.player.PlaybackState
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.f_player.service.PlayerService

class AppNotificationManager(
    private val service: Service,
    private val token: MediaSessionCompat.Token,
    private val notificationManager: NotificationManagerCompat
) {

    companion object {
        private const val NOTIFICATION_ID = 100

        private const val PLAYER_PENDING_INTENT_ID = 10
        private const val PAUSE_PENDING_INTENT_ID = 20
        private const val PLAY_PENDING_INTENT_ID = 30
        private const val PLAY_NEXT_PENDING_INTENT_ID = 40
        private const val PLAY_PREV_PENDING_INTENT_ID = 50
        private const val STOP_PENDING_INTENT_ID = 60
    }

    private val channelId: String = service.getString(R.string.app_name)
    private var intent: Intent? = null

    @Volatile
    private var hasStarted: Boolean = false
    private var handler = Handler()
    private var state: PlaybackState = PlaybackState.Idle
    private var media: Episode? = null

    init {
        createNotificationChannels()
    }

    fun setIntent(intent: Intent?) {
        this.intent = intent
    }

    fun updateMedia(media: Episode?) {
        this.media = media
    }

    fun updateState(state: PlaybackState) {
        this.state = state
        when (state) {
            is PlaybackState.Buffering -> updateNotification()
            is PlaybackState.Playing -> updateNotification()
            is PlaybackState.Paused -> pauseNotification()
            is PlaybackState.Completed -> pauseNotification()
            else -> stopNotification()
        }
    }

    private fun updateNotification() {
        if (!hasStarted) {
            val intent = Intent(service, PlayerService::class.java)
            ContextCompat.startForegroundService(service, intent)
        }
        startNotification()
    }

    private fun pauseNotification() {
        startNotification()
        handler.postDelayed({
            service.stopForeground(false)
            hasStarted = false
        }, 100)
    }

    private fun stopNotification() {
        hasStarted = false
        media = null
        notificationManager.cancel(NOTIFICATION_ID)
        service.stopSelf()
    }

    private fun pause(context: Context): NotificationCompat.Action {
        val pendingIntent = getPendingIntent(
            context,
            PAUSE_PENDING_INTENT_ID,
            PlayerService.ACTION_PAUSE
        )
        return NotificationCompat.Action(R.drawable.ic_pause, "Pause", pendingIntent)
    }

    private fun next(context: Context): NotificationCompat.Action {
        val pendingIntent = getPendingIntent(
            context,
            PLAY_NEXT_PENDING_INTENT_ID,
            PlayerService.ACTION_NEXT
        )
        return NotificationCompat.Action(R.drawable.ic_next, "Next", pendingIntent)
    }

    private fun prev(context: Context): NotificationCompat.Action {
        val pendingIntent = getPendingIntent(
            context,
            PLAY_PREV_PENDING_INTENT_ID,
            PlayerService.ACTION_PREV
        )
        return NotificationCompat.Action(R.drawable.ic_previous, "Previous", pendingIntent)
    }

    private fun play(context: Context): NotificationCompat.Action {
        val pendingIntent = getPendingIntent(
            context,
            PLAY_PENDING_INTENT_ID,
            PlayerService.ACTION_PLAY
        )
        return NotificationCompat.Action(R.drawable.ic_play, "Start", pendingIntent)
    }

    private fun dismiss(context: Context): PendingIntent {
        return getPendingIntent(
            context,
            STOP_PENDING_INTENT_ID,
            PlayerService.ACTION_STOP
        )
    }

    private fun getPendingIntent(context: Context, intentId: Int, action: String): PendingIntent {
        val prevIntent = Intent(context, PlayerService::class.java)
        prevIntent.action = action

        return PendingIntent.getService(context, intentId, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun startNotification() {
        val builder = createBuilder()
        Glide.with(service)
            .asBitmap()
            .load(media?.image)
            .apply(
                RequestOptions
                    .diskCacheStrategyOf(DiskCacheStrategy.DATA)
                    .onlyRetrieveFromCache(true)
                    .priority(Priority.IMMEDIATE)
            )
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    showNotification(builder, resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    showNotification(builder, null)
                }
            })
    }

    private fun showNotification(builder: NotificationCompat.Builder, bitmap: Bitmap?) {
        if (media == null || state == PlaybackState.Idle) {
            return
        }

        builder.setStyle(
            androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(token)
                .setShowActionsInCompactView(0, 1, 2)
            )
            .setPriority(Notification.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setUsesChronometer(false)
            .setSmallIcon(R.drawable.ic_play)
            .setShowWhen(false)
            .setOnlyAlertOnce(true)
            .setContentTitle(media?.title)
            .setContentText(media?.podcastTitle)
            .setDeleteIntent(dismiss(service))
            .addAction(prev(service))

        if (state is PlaybackState.Paused || state is PlaybackState.Completed) {
            builder.addAction(play(service))
            builder.setOngoing(false)
        } else {
            builder.addAction(pause(service))
            builder.setOngoing(true)
        }
        builder.addAction(next(service))
        builder.setLargeIcon(bitmap)

        val notificationIntent = intent ?: Intent()
        builder.setContentIntent(
            PendingIntent.getActivity(
                service,
                PLAYER_PENDING_INTENT_ID, notificationIntent, 0
            )
        )
        notify(builder.build())
    }

    private fun notify(notification: Notification) {
        notificationManager.notify(NOTIFICATION_ID, notification)
        if (!hasStarted) {
            service.startForeground(NOTIFICATION_ID, notification)
            hasStarted = true
        }
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
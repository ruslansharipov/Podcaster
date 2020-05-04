package ru.sharipov.podcaster.f_player.service.di

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.media.AudioManager
import android.net.wifi.WifiManager
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.f_player.notification.AppNotificationManager
import ru.sharipov.podcaster.f_player.playback.PlaybackImpl
import ru.sharipov.podcaster.f_player.playback.PlaybackInterface
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerServiceBus
import ru.sharipov.podcaster.f_player.service.PlayerService
import ru.sharipov.podcaster.f_player.media.MediaManager
import ru.sharipov.podcaster.f_player.media.MediaQueue
import ru.sharipov.podcaster.f_player.media.MediaSessionCallback

@Module
class PlayerModule(
    private val service: PlayerService
) {

    @Provides
    @PerService
    fun providesService(): Service = service

    @Provides
    @PerService
    fun providesServiceCallback(): PlaybackInterface.ServiceCallback = service

    @Provides
    @PerService
    fun providesMediaSessionCallback(playerServiceBus: PlayerServiceBus): MediaSessionCallback {
        return MediaSessionCallback(
            playerServiceBus
        )
    }

    @Provides
    @PerService
    fun providesMediaSession(
        context: Context,
        mediaSessionCallback: MediaSessionCallback
    ): MediaSessionCompat {
        val receiverComponentName = ComponentName(context.packageName, javaClass.name)
        return MediaSessionCompat(context, javaClass.name, receiverComponentName, null).apply {
            setCallback(mediaSessionCallback)
            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        }
    }

    @Provides
    @PerService
    fun providesNotificationManager(
        context: Context,
        service: Service,
        mediaSessionCompat: MediaSessionCompat
    ): AppNotificationManager {
        val notificationManager = NotificationManagerCompat.from(context)
        return AppNotificationManager(
            service,
            mediaSessionCompat.sessionToken,
            notificationManager
        )
    }

    @Provides
    @PerService
    fun providesMediaManager(
        context: Context,
        serviceCallback: PlaybackInterface.ServiceCallback,
        playerServiceBus: PlayerServiceBus
    ): MediaManager {
        val mediaQueue = MediaQueue()
        val wifiLock =
            (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
                .createWifiLock(WifiManager.WIFI_MODE_FULL, context.getString(R.string.app_name))
        val audioManager =
            context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val playerCallback = PlaybackImpl(context, audioManager, wifiLock)
        return MediaManager(
            playerServiceBus,
            mediaQueue,
            playerCallback,
            serviceCallback
        ).apply { playerCallback.setCallback(this) }
    }
}

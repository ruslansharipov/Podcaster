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
import ru.sharipov.podcaster.base_feature.ui.bus.PlayerServiceBus
import ru.sharipov.podcaster.f_player.service.PlayerService
import ru.sharipov.podcaster.f_player.media.MediaManager
import ru.sharipov.podcaster.f_player.media.MediaSessionCallback
import ru.sharipov.podcaster.i_history.HistoryInteractor

@Module
class PlayerModule(
    private val service: PlayerService
) {

    @Provides
    @PerService
    fun providesService(): Service = service

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
        playerServiceBus: PlayerServiceBus,
        notificationManager: AppNotificationManager,
        mediaSession: MediaSessionCompat,
        historyInteractor: HistoryInteractor
    ): MediaManager {
        val audioManager =
            context.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val wifiLock =
            (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
                .createWifiLock(WifiManager.WIFI_MODE_FULL, context.getString(R.string.app_name))
        return MediaManager(
            context,
            audioManager,
            wifiLock,
            playerServiceBus,
            notificationManager,
            mediaSession,
            historyInteractor
        )
    }
}

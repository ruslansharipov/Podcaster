package ru.sharipov.podcaster.base_feature.application.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.sharipov.podcaster.base_feature.R
import ru.sharipov.podcaster.base_feature.application.app.di.AppInjector
import ru.sharipov.podcaster.base_feature.ui.logger.TimberLoggingStrategy
import ru.surfstudio.android.activity.holder.ActiveActivityHolder
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks
import ru.surfstudio.android.utilktx.ktx.ui.activity.ActivityLifecycleListener

class App: MultiDexApplication() {

    val activityHolder = ActiveActivityHolder()

    override fun onCreate() {
        super.onCreate()

        AppInjector.initInjector(this)

        initLogger()
        initThreeTenAbp()
        registerActiveActivityListener()
        registerNavigationProviderCallbacks()
        createNotificationChannels()
    }

    private fun initLogger() {
        Logger.addLoggingStrategy(TimberLoggingStrategy())
    }

    private fun initThreeTenAbp() {
        AndroidThreeTen.init(this)
    }

    /**
     * Регистрирует слушатель аткивной активити
     */
    private fun registerActiveActivityListener() {
        registerActivityLifecycleCallbacks(
            ActivityLifecycleListener(
                onActivityResumed = { activityHolder.activity = it },
                onActivityPaused = { activityHolder.clearActivity() }
            )
        )
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId: String = getString(R.string.app_name)
            val notificationManager = NotificationManagerCompat.from(this)
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

    private fun registerNavigationProviderCallbacks() {
        val provider = AppInjector.appComponent.activityNavigationProvider()
        val callbackProvider = provider as? ActivityNavigationProviderCallbacks ?: return
        registerActivityLifecycleCallbacks(callbackProvider)
    }

}
package ru.sharipov.podcaster.base_feature.application.app

import androidx.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.sharipov.podcaster.base_feature.application.app.di.AppInjector
import ru.sharipov.podcaster.base_feature.ui.logger.TimberLoggingStrategy
import ru.surfstudio.android.activity.holder.ActiveActivityHolder
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.utilktx.ktx.ui.activity.ActivityLifecycleListener

class App: MultiDexApplication() {

    val activityHolder = ActiveActivityHolder()

    override fun onCreate() {
        super.onCreate()

        AppInjector.initInjector(this)

        initLogger()
        initThreeTenAbp()
        registerActiveActivityListener()
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

}
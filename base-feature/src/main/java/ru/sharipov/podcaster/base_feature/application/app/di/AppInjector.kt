package ru.sharipov.podcaster.base_feature.application.app.di

import ru.sharipov.podcaster.base_feature.application.app.App

/**
 * Объект ответственный за создание и хранение [AppComponent]
 */
object AppInjector {

    lateinit var appComponent: AppComponent

    fun initInjector(app: App) {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(app, app.activityHolder))
                .build()
    }
}
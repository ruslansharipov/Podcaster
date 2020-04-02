package ru.sharipov.podcaster.base_feature.ui.di

import android.app.Activity
import android.content.Intent
import ru.surfstudio.android.core.mvp.configurator.BaseActivityViewConfigurator
import ru.sharipov.podcaster.base_feature.application.app.di.AppComponent
import ru.sharipov.podcaster.base_feature.application.app.di.AppInjector
import ru.sharipov.podcaster.base_feature.ui.screen.ActivityScreenModule

/**
 * Базовый конфигуратор для экрана, основанного на [Activity]
 */
abstract class ActivityScreenConfigurator(
        intent: Intent
) : BaseActivityViewConfigurator<AppComponent, ActivityComponent, ActivityScreenModule>(intent) {

    override fun createActivityComponent(parentComponent: AppComponent): ActivityComponent {
        return DaggerActivityComponent.builder()
                .appComponent(parentComponent)
                .activityModule(ActivityModule(persistentScope))
                .build()
    }

    override fun getParentComponent(): AppComponent {
        return AppInjector.appComponent
    }

    override fun getActivityScreenModule(): ActivityScreenModule {
        return ActivityScreenModule(persistentScope)
    }
}
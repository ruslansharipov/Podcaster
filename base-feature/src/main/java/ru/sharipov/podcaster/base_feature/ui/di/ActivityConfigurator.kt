package ru.sharipov.podcaster.base_feature.ui.di

import android.content.Context
import ru.sharipov.podcaster.base_feature.application.app.di.AppComponent
import ru.sharipov.podcaster.base_feature.application.app.di.AppInjector
import ru.surfstudio.android.core.ui.configurator.BaseActivityConfigurator

class ActivityConfigurator(
        val context: Context
) : BaseActivityConfigurator<ActivityComponent, AppComponent>() {

    override fun createActivityComponent(parentComponent: AppComponent?): ActivityComponent =
            DaggerActivityComponent.builder()
                    .appComponent(parentComponent)
                    .build()

    override fun getParentComponent(): AppComponent = AppInjector.appComponent
}
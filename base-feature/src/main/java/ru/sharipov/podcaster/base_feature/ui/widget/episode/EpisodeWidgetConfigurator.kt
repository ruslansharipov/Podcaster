package ru.sharipov.podcaster.base_feature.ui.widget.episode

import dagger.Component
import dagger.Module
import ru.sharipov.podcaster.base_feature.ui.di.ActivityComponent
import ru.sharipov.podcaster.base_feature.ui.screen.FragmentScreenModule
import ru.surfstudio.android.core.mvp.binding.rx.ui.CoreRxConstraintLayoutView
import ru.surfstudio.android.core.mvp.configurator.ScreenComponent
import ru.surfstudio.android.core.ui.activity.CoreActivityInterface
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.mvp.widget.configurator.BaseWidgetViewConfigurator

class EpisodeWidgetConfigurator : BaseWidgetViewConfigurator<ActivityComponent, EpisodeWidgetConfigurator.EpisodeModule>() {

    @PerScreen
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [FragmentScreenModule::class, EpisodeModule::class]
    )
    interface EpisodeWidgetComponent: ScreenComponent<EpisodeView>

    @Module
    class EpisodeModule

    override fun getWidgetScreenModule(): EpisodeModule = EpisodeModule()

    override fun createScreenComponent(
        parentComponent: ActivityComponent?,
        widgetScreenModule: EpisodeModule?
    ): ScreenComponent<*> {
        return DaggerEpisodeWidgetConfigurator_EpisodeWidgetComponent.builder()
            .activityComponent(parentComponent)
            .episodeModule(EpisodeModule())
            .build()
    }

    override fun getParentComponent(): ActivityComponent {
        return (getTargetWidgetView<CoreRxConstraintLayoutView>().context as CoreActivityInterface)
            .persistentScope
            .configurator
            .activityComponent as ActivityComponent
    }

}

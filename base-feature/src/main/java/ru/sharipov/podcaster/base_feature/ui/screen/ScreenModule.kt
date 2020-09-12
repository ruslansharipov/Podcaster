package ru.sharipov.podcaster.base_feature.ui.screen

import dagger.Module
import dagger.Provides
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.base.reducer.StateReducerDependency
import ru.sharipov.podcaster.base_feature.ui.screen.navigation.NavigationScreenModule
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.core.mvp.error.ErrorHandler
import ru.surfstudio.android.core.mvp.presenter.BasePresenterDependency
import ru.surfstudio.android.core.ui.event.ScreenEventDelegateManager
import ru.surfstudio.android.core.ui.navigation.activity.navigator.ActivityNavigator
import ru.surfstudio.android.core.ui.state.ScreenState
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider

/**
 * Общий модуль для зависимостей Activity и Fragment
 */
@Module(includes = [NavigationScreenModule::class])
abstract class ScreenModule {

    @PerScreen
    @Provides
    internal fun provideReducerDependency(
        errorHandler: ErrorHandler
    ) : StateReducerDependency {
        return StateReducerDependency(
            errorHandler
        )
    }

    @PerScreen
    @Provides
    internal fun providePresenterDependency(
        eventDelegateManager: ScreenEventDelegateManager,
        schedulersProvider: SchedulersProvider,
        screenState: ScreenState
    ) : StatePresenterDependency {
        return StatePresenterDependency(
            eventDelegateManager,
            schedulersProvider,
            screenState
        )
    }

    @PerScreen
    @Provides
    internal fun provideBaseDependency(
        schedulersProvider: SchedulersProvider,
        screenState: ScreenState,
        eventDelegateManager: ScreenEventDelegateManager,
        errorHandler: ErrorHandler,
        connectionProvider: ConnectionProvider,
        activityNavigator: ActivityNavigator
    ): BasePresenterDependency {
        return BasePresenterDependency(
            schedulersProvider,
            screenState,
            eventDelegateManager,
            errorHandler,
            connectionProvider,
            activityNavigator
        )
    }
}

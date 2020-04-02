package ru.sharipov.podcaster.base_feature.ui.screen

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.sharipov.podcaster.base_feature.ui.error.ErrorHandlerModule
import ru.sharipov.podcaster.base_feature.ui.message_controller.IconMessageController
import ru.sharipov.podcaster.base_feature.ui.message_controller.TopSnackController
import ru.surfstudio.android.core.mvp.scope.ActivityViewPersistentScope
import ru.surfstudio.android.core.ui.event.ScreenEventDelegateManager
import ru.surfstudio.android.core.ui.navigation.activity.navigator.ActivityNavigator
import ru.surfstudio.android.core.ui.navigation.activity.navigator.ActivityNavigatorForActivity
import ru.surfstudio.android.core.ui.navigation.feature.installer.SplitFeatureInstaller
import ru.surfstudio.android.core.ui.permission.PermissionManager
import ru.surfstudio.android.core.ui.permission.PermissionManagerForActivity
import ru.surfstudio.android.core.ui.provider.ActivityProvider
import ru.surfstudio.android.core.ui.scope.ActivityPersistentScope
import ru.surfstudio.android.core.ui.scope.ScreenPersistentScope
import ru.surfstudio.android.core.ui.state.ScreenState
import ru.surfstudio.android.dagger.scope.PerActivity
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigator
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigatorForActivity
import ru.surfstudio.android.shared.pref.NO_BACKUP_SHARED_PREF
import javax.inject.Named

@Module(includes = [ErrorHandlerModule::class])
class ActivityScreenModule(
    private val activityViewPersistentScope: ActivityViewPersistentScope
) : ScreenModule() {

    @Provides
    @PerScreen
    internal fun providePersistentScope(persistentScope: ActivityPersistentScope): ScreenPersistentScope {
        return persistentScope
    }

    @Provides
    @PerScreen
    internal fun provideScreenState(persistentScope: ActivityPersistentScope): ScreenState {
        return persistentScope.screenState
    }

    @Provides
    @PerScreen
    internal fun provideEventDelegateManagerProvider(
        persistentScope: ScreenPersistentScope
    ): ScreenEventDelegateManager {
        return persistentScope.screenEventDelegateManager
    }

    @Provides
    @PerScreen
    internal fun provideActivityNavigator(
        activityProvider: ActivityProvider,
        eventDelegateManager: ScreenEventDelegateManager
    ): ActivityNavigator {
        return ActivityNavigatorForActivity(activityProvider, eventDelegateManager)
    }

    @Provides
    @PerScreen
    internal fun provideSplitFeatureInstaller(context: Context): SplitFeatureInstaller {
        return SplitFeatureInstaller(context)
    }

    @Provides
    @PerScreen
    internal fun providePermissionManager(
        eventDelegateManager: ScreenEventDelegateManager,
        activityNavigator: ActivityNavigator,
        @Named(NO_BACKUP_SHARED_PREF) sharedPreferences: SharedPreferences,
        activityProvider: ActivityProvider
    ): PermissionManager {
        return PermissionManagerForActivity(
            eventDelegateManager,
            activityNavigator,
            sharedPreferences,
            activityProvider
        )
    }

    @Provides
    @PerScreen
    internal fun provideMessageController(activityProvider: ActivityProvider): IconMessageController {
        return TopSnackController(activityProvider)
    }

    @Provides
    @PerScreen
    internal fun provideDialogNavigator(activityProvider: ActivityProvider): DialogNavigator {
        return DialogNavigatorForActivity(activityProvider, activityViewPersistentScope)
    }
}

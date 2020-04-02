package ru.sharipov.podcaster.base_feature.ui.di

import ru.surfstudio.android.core.ui.navigation.fragment.FragmentNavigator
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.core.ui.permission.PermissionManager
import ru.surfstudio.android.core.ui.provider.ActivityProvider
import ru.surfstudio.android.core.ui.scope.ActivityPersistentScope
import ru.surfstudio.android.core.ui.state.ActivityScreenState
import ru.surfstudio.android.dagger.scope.PerActivity

/**
 * Интерфейс, объединяющий в себе все зависимости в скоупе [PerActivity]
 * Следует использовать в компоненте Activity и других компонентах более высоких уровней,
 * зависящих от него.
 */
interface ActivityProxyDependencies {

    fun activityPersistentScope(): ActivityPersistentScope
    fun activityScreenState(): ActivityScreenState
    fun activityProvider(): ActivityProvider

    fun fragmentNavigator(): FragmentNavigator
    fun tabFragmentNavigator(): TabFragmentNavigator

    fun permissionManager(): PermissionManager
}

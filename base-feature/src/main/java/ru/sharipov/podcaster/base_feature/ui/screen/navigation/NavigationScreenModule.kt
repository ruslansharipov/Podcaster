package ru.sharipov.podcaster.base_feature.ui.screen.navigation

import dagger.Module
import dagger.Provides
import ru.sharipov.podcaster.base_feature.ui.fragment.FragmentProviderImpl
import ru.surfstudio.android.core.ui.scope.ScreenPersistentScope
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.provider.FragmentProvider

@Module
open class NavigationScreenModule {

    @Provides
    @PerScreen
    fun provideFragmentProvider(persistentScope: ScreenPersistentScope): FragmentProvider {
        return FragmentProviderImpl(persistentScope)
    }
}

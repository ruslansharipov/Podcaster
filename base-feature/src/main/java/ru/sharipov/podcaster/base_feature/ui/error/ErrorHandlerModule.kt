package ru.sharipov.podcaster.base_feature.ui.error

import dagger.Module
import dagger.Provides
import ru.surfstudio.android.core.mvp.error.ErrorHandler
import ru.surfstudio.android.dagger.scope.PerScreen

@Module
class ErrorHandlerModule {

    @Provides
    @PerScreen
    fun provideErrorHandler(standardErrorHandler: StandardErrorHandler): ErrorHandler {
        return standardErrorHandler
    }
}
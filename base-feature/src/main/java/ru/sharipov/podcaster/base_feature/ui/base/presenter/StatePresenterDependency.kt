package ru.sharipov.podcaster.base_feature.ui.base.presenter

import ru.surfstudio.android.core.ui.event.ScreenEventDelegateManager
import ru.surfstudio.android.core.ui.state.ScreenState
import ru.surfstudio.android.rx.extension.scheduler.SchedulersProvider

class StatePresenterDependency(
    val eventDelegateManager: ScreenEventDelegateManager,
    val schedulersProvider: SchedulersProvider,
    val screenState: ScreenState
)

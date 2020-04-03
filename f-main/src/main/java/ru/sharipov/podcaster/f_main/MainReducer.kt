package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.StateReducer
import ru.surfstudio.android.core.mvp.binding.rx.relation.mvp.State
import ru.surfstudio.android.core.mvp.error.ErrorHandler
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

class MainState

@PerScreen
class MainStateHolder @Inject constructor() : State<MainState>(MainState())

@PerScreen
class MainReducer @Inject constructor(errorHandler: ErrorHandler) :
    StateReducer(errorHandler) {

}
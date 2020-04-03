package ru.sharipov.podcaster.f_main

import ru.sharipov.podcaster.base_feature.ui.base.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.StatePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor(baseDependency: StatePresenterDependency) :
    StatePresenter(baseDependency) {


}
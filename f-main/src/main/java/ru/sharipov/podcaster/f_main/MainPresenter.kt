package ru.sharipov.podcaster.f_main

import ru.surfstudio.android.core.mvp.presenter.BasePresenter
import ru.surfstudio.android.core.mvp.presenter.BasePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor(basePresenterDependency: BasePresenterDependency) :
    BasePresenter<MainActivityView>(basePresenterDependency) {


}
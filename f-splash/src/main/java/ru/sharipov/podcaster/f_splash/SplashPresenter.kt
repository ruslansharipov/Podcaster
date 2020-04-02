package ru.sharipov.podcaster.f_splash

import ru.sharipov.podcaster.base_feature.ui.navigation.MainActivityRoute
import ru.surfstudio.android.core.mvp.presenter.BasePresenter
import ru.surfstudio.android.core.mvp.presenter.BasePresenterDependency
import ru.surfstudio.android.core.ui.navigation.activity.navigator.ActivityNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class SplashPresenter @Inject constructor(
    basePresenterDependency: BasePresenterDependency,
    private val activityNavigator: ActivityNavigator
) : BasePresenter<SplashActivityView>(basePresenterDependency) {

    override fun onFirstLoad() {
        super.onFirstLoad()
        activityNavigator.start(MainActivityRoute())
        activityNavigator.finishCurrent()
    }
}

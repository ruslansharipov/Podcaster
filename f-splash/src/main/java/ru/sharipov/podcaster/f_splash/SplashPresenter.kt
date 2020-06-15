package ru.sharipov.podcaster.f_splash

import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainActivityRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainTabType
import ru.sharipov.podcaster.i_subscription.SubscriptionInteractor
import ru.surfstudio.android.core.mvp.presenter.BasePresenter
import ru.surfstudio.android.core.mvp.presenter.BasePresenterDependency
import ru.surfstudio.android.core.ui.navigation.activity.navigator.ActivityNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class SplashPresenter @Inject constructor(
    baseDependency: BasePresenterDependency,
    private val activityNavigator: ActivityNavigator,
    private val subscriptionInteractor: SubscriptionInteractor
) : BasePresenter<SplashActivityView>(baseDependency) {

    override fun onFirstLoad() {
        super.onFirstLoad()
        subscribe(subscriptionInteractor.observeSubscriptions()){
            val tabType = if (it.isEmpty()) MainTabType.EXPLORE else MainTabType.PROFILE
            activityNavigator.start(MainActivityRoute(tabType))
            activityNavigator.finishCurrent()
        }
    }
}

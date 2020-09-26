package ru.sharipov.podcaster.f_splash

import ru.sharipov.podcaster.base_feature.ui.extesions.execute
import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainActivityRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainTabType
import ru.sharipov.podcaster.i_subscription.SubscriptionInteractor
import ru.surfstudio.android.core.mvp.presenter.BasePresenter
import ru.surfstudio.android.core.mvp.presenter.BasePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.command.activity.FinishAffinity
import ru.surfstudio.android.navigation.command.activity.Start
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

@PerScreen
class SplashPresenter @Inject constructor(
    baseDependency: BasePresenterDependency,
    private val navigationExecutor: AppCommandExecutor,
    private val subscriptionInteractor: SubscriptionInteractor
) : BasePresenter<SplashActivityView>(baseDependency) {

    override fun onFirstLoad() {
        super.onFirstLoad()
        subscribe(subscriptionInteractor.observeSubscriptions()) {
            val tabType = if (it.isEmpty()) MainTabType.EXPLORE else MainTabType.FEED
            navigationExecutor.execute(FinishAffinity(), Start(MainActivityRoute(tabType)))
        }
    }
}

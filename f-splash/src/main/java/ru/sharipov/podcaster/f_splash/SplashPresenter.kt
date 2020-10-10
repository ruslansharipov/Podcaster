package ru.sharipov.podcaster.f_splash

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.execute
import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainActivityRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.main.MainTabType
import ru.sharipov.podcaster.i_subscription.SubscriptionInteractor
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.command.activity.FinishAffinity
import ru.surfstudio.android.navigation.command.activity.Start
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

@PerScreen
class SplashPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val navigationExecutor: AppCommandExecutor,
    private val subscriptionInteractor: SubscriptionInteractor
) : StatePresenter(dependency) {

    override fun onFirstLoad() {
        super.onFirstLoad()
        subscriptionInteractor
            .observeSubscriptions()
            .subscribeIoDefault {
                val tabType = if (it.isEmpty()) MainTabType.EXPLORE else MainTabType.FEED
                navigationExecutor.execute(FinishAffinity(), Start(MainActivityRoute(tabType)))
            }
    }
}

package ru.sharipov.podcaster.f_subscription

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class SubscriptionsPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: SubscriptionsReducer
): StatePresenter(dependency) {

}

package ru.sharipov.podcaster.f_subscription

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.domain.PodcastFull
import ru.sharipov.podcaster.i_subscription.SubscriptionInteractor
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import javax.inject.Inject

@PerScreen
class SubscriptionsPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: SubscriptionsReducer,
    private val subscriptionInteractor: SubscriptionInteractor,
    private val tabNavigator: TabFragmentNavigator
) : StatePresenter(dependency) {

    init {
        subscribeOnSubscriptionsChange()
    }

    fun onSubscriptionClick(podcast: PodcastFull) {
        tabNavigator.open(PodcastFragmentRoute(podcast))
    }

    private fun subscribeOnSubscriptionsChange() {
        subscriptionInteractor
            .observeSubscriptions()
            .subscribeIoDefault(reducer::onSubscriptionChanged)
    }
}

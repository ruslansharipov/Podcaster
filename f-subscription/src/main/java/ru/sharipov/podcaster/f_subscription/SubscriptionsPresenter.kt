package ru.sharipov.podcaster.f_subscription

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.PodcastFull
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.sharipov.podcaster.i_subscription.SubscriptionInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.core.ui.navigation.fragment.tabfragment.TabFragmentNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.mvp.dialog.navigation.navigator.DialogNavigator
import javax.inject.Inject

@PerScreen
class SubscriptionsPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: SubscriptionsReducer,
    private val subscriptionInteractor: SubscriptionInteractor,
    private val podcastInteractor: PodcastInteractor,
    private val tabNavigator: TabFragmentNavigator,
    private val dialogNavigator: DialogNavigator
) : StatePresenter(dependency) {

    override fun onFirstLoad() {
        subscribeOnSubscriptionsChange()
        fetchNewEpisodes()
    }

    fun onSubscriptionClick(podcast: PodcastFull) {
        tabNavigator.open(PodcastFragmentRoute(podcast))
    }

    private fun subscribeOnSubscriptionsChange() {
        subscriptionInteractor
            .observeSubscriptions()
            .subscribeIoDefault(reducer::onSubscriptionChanged)
    }

    private fun fetchNewEpisodes(){
        subscriptionInteractor
            .observeSubscriptions()
            .flatMap(podcastInteractor::getNewEpisodes)
            .asRequest()
            .subscribeIoDefault { reducer.onEpisoedsLoaded(it) }
    }

    fun onEpisodeClick(episode: Episode) {
        dialogNavigator.show(EpisodeFragmentRoute(episode))
    }
}

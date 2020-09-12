package ru.sharipov.podcaster.f_subscription

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.replace
import ru.sharipov.podcaster.base_feature.ui.extesions.show
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.PodcastFull
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.sharipov.podcaster.i_subscription.SubscriptionInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.NavigationCommandExecutor
import javax.inject.Inject

@PerScreen
class SubscriptionsPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: SubscriptionsReducer,
    private val subscriptionInteractor: SubscriptionInteractor,
    private val podcastInteractor: PodcastInteractor,
    private val navigationExecutor: NavigationCommandExecutor
) : StatePresenter(dependency) {

    override fun onFirstLoad() {
        subscribeOnSubscriptionsChange()
        subscribeOnNewEpisodesLoading()
    }

    fun onSubscriptionClick(podcast: PodcastFull) {
        navigationExecutor.replace(PodcastFragmentRoute(podcast))
    }

    fun onEpisodeClick(episode: Episode) {
        navigationExecutor.show(EpisodeFragmentRoute(episode))
    }

    private fun subscribeOnSubscriptionsChange() {
        subscriptionInteractor
            .observeSubscriptions()
            .subscribeIoDefault(reducer::onSubscriptionChanged)
    }

    private fun subscribeOnNewEpisodesLoading(){
        subscriptionInteractor
            .observeSubscriptions()
            .switchMap { subscriptions ->
                podcastInteractor
                    .getNewEpisodes(subscriptions)
                    .io()
            }
            .asRequest()
            .subscribeIoDefault(reducer::onEpisoedsLoaded)
    }
}

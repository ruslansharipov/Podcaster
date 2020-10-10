package ru.sharipov.podcaster.f_subscription

import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenter
import ru.sharipov.podcaster.base_feature.ui.base.presenter.StatePresenterDependency
import ru.sharipov.podcaster.base_feature.ui.extesions.replace
import ru.sharipov.podcaster.base_feature.ui.extesions.show
import ru.sharipov.podcaster.base_feature.ui.navigation.EpisodeFragmentRoute
import ru.sharipov.podcaster.base_feature.ui.navigation.PodcastFragmentRoute
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.domain.Subscription
import ru.sharipov.podcaster.i_listen.PodcastInteractor
import ru.sharipov.podcaster.i_subscription.SubscriptionInteractor
import ru.surfstudio.android.core.mvp.binding.rx.request.type.asRequest
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

@PerScreen
class SubscriptionsPresenter @Inject constructor(
    dependency: StatePresenterDependency,
    private val reducer: SubscriptionsReducer,
    private val subscriptionInteractor: SubscriptionInteractor,
    private val podcastInteractor: PodcastInteractor,
    private val navigationExecutor: AppCommandExecutor
) : StatePresenter(dependency) {

    override fun onFirstLoad() {
        subscribeOnSubscriptionsChange()
        subscribeOnNewEpisodesLoading()
        // todo загрузка новых эпизодов по свайп рефреш
    }

    fun onSubscriptionClick(podcast: Subscription) {
        navigationExecutor.replace(PodcastFragmentRoute(podcast))
    }

    fun onEpisodeClick(episode: Episode) {
        navigationExecutor.show(route = EpisodeFragmentRoute(episode))
    }

    private fun subscribeOnSubscriptionsChange() {
        subscriptionInteractor
            .observeSubscriptions()
            .subscribeIoDefault(reducer::onSubscriptionChanged)
    }

    private fun subscribeOnNewEpisodesLoading(){
        subscriptionInteractor
            .observeSubscriptions()
            .firstOrError()
            .flatMapObservable { subscriptions ->
                podcastInteractor
                    .getNewEpisodes(subscriptions)
                    .io()
            }
            .asRequest()
            .subscribeIoDefault(reducer::onEpisoedsLoaded)
    }
}

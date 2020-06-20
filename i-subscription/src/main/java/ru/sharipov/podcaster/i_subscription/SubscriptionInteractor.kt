package ru.sharipov.podcaster.i_subscription

import io.reactivex.Observable
import ru.sharipov.podcaster.domain.PodcastFull
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Interactor for saving, removing and observing user's subscriptions
 */
@PerApplication
class SubscriptionInteractor @Inject constructor(
    private val subscriptionStorage: SubscriptionStorage
) {

    /**
     * Observe podcast's subscription state changes
     *
     * @param podcastId id of the target podcast
     */
    fun observeIsSubscribed(podcastId: String): Observable<Boolean> {
        return subscriptionStorage
            .observeSubscriptions()
            .map { subscriptions -> subscriptions.any { it.id == podcastId } }
    }

    /** [SubscriptionStorage.observeSubscriptions] */
    fun observeSubscriptions(): Observable<List<PodcastFull>> {
        return subscriptionStorage.observeSubscriptions()
    }

    /** [SubscriptionStorage.add] */
    fun add(podcastFull: PodcastFull) {
        subscriptionStorage.add(podcastFull)
    }

    /** [SubscriptionStorage.remove] */
    fun remove(podcastFull: PodcastFull) {
        subscriptionStorage.remove(podcastFull)
    }
}
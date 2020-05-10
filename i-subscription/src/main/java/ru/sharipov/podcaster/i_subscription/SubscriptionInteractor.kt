package ru.sharipov.podcaster.i_subscription

import io.reactivex.Observable
import io.reactivex.Single
import ru.sharipov.podcaster.domain.PodcastFull
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class SubscriptionInteractor @Inject constructor(
    private val subscriptionStorage: SubscriptionStorage
) {

    fun observeIsSubscribed(podcastId: String): Observable<Boolean> {
        return subscriptionStorage
            .observeSubscriptions()
            .map { subscriptions -> subscriptions.any { it.id == podcastId } }
    }

    fun observeSubscriptions(): Observable<List<Subscription>> {
        return subscriptionStorage.observeSubscriptions()
    }

    fun getSubscriptions(): List<Subscription> {
        return subscriptionStorage.getSubscriptions()
    }

    fun getSubscriptionIds(): List<String> {
        return subscriptionStorage.getSubscribedIds()
    }

    fun add(podcastFull: PodcastFull) {
        subscriptionStorage.add(podcastFull.toSubscription())
    }

    fun remove(podcastFull: PodcastFull) {
        subscriptionStorage.remove(podcastFull.toSubscription())
    }
}
package ru.sharipov.podcaster.i_subscription

import io.reactivex.Observable
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

    fun observeSubscriptions(): Observable<List<PodcastFull>> {
        return subscriptionStorage.observeSubscriptions()
    }

    fun add(podcastFull: PodcastFull) {
        subscriptionStorage.add(podcastFull)
    }

    fun remove(podcastFull: PodcastFull) {
        subscriptionStorage.remove(podcastFull)
    }
}
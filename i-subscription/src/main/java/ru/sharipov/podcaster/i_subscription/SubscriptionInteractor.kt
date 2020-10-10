package ru.sharipov.podcaster.i_subscription

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.sharipov.podcaster.domain.PodcastShort
import ru.sharipov.podcaster.domain.Subscription
import ru.sharipov.podcaster.i_history.dao.SubscriptionDao
import ru.sharipov.podcaster.i_history.entity.toSubscriptionEntity
import ru.sharipov.podcaster.i_network.network.transformCollection
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Interactor for saving, removing and observing user's subscriptions
 */
@PerApplication
class SubscriptionInteractor @Inject constructor(
    private val subscriptionDao: SubscriptionDao
) {

    /**
     * Observe podcast's subscription state changes
     *
     * @param podcastId id of the target podcast
     */
    fun observeIsSubscribed(podcastId: String): Flowable<Boolean> {
        return subscriptionDao.observeIsSubscribed(podcastId)
    }

    fun observeSubscriptions(): Flowable<List<PodcastShort>> {
        return subscriptionDao.observeSubscriptionEntities().transformCollection()
    }

    fun add(subscription: Subscription): Completable {
        return subscriptionDao.insertSubscription(subscription.toSubscriptionEntity())
    }

    fun remove(subscription: Subscription): Completable {
        return subscriptionDao.deleteSubscription(subscription.toSubscriptionEntity())
    }
}
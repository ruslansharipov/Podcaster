package ru.sharipov.podcaster.i_subscription

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.domain.PodcastFull
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage

/**
 * Storage of user's subscriptions
 */
class SubscriptionStorage(
    cacheDir: String,
    namingProcessor: NamingProcessor
) : BaseJsonFileStorage<PodcastFull>(
    FileProcessor(cacheDir, STORAGE_DIR, STORAGE_MAX_FILES),
    namingProcessor,
    PodcastFull::class.java
) {

    private companion object {
        const val STORAGE_DIR = "subscription_storage"
        const val STORAGE_MAX_FILES = 255
    }

    private val subscriptionsRelay = BehaviorRelay.createDefault<List<PodcastFull>>(all)

    /**
     * Observe user's subscriptions
     */
    fun observeSubscriptions(): Observable<List<PodcastFull>> {
        return subscriptionsRelay.hide()
    }

    /**
     * Add subscription
     *
     * @param podcastFull   subscription to add
     */
    fun add(podcastFull: PodcastFull) {
        put(podcastFull.id, podcastFull)
        subscriptionsRelay.accept(all)
    }

    /**
     * Remove [podcastFull] from subscriptions
     */
    fun remove(podcastFull: PodcastFull) {
        remove(podcastFull.id)
        subscriptionsRelay.accept(all)
    }

    /**
     * Synchronous getter for subscriptions
     */
    fun getSubscriptions(): List<PodcastFull> {
        return all
    }

    /**
     * Synchronous getter for subscription ids
     */
    fun getSubscribedIds(): List<String> {
        return all.map(PodcastFull::id)
    }
}

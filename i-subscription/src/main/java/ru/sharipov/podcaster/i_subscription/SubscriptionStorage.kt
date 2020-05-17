package ru.sharipov.podcaster.i_subscription

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.domain.PodcastFull
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage

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

    private val subscriptionsRelay = PublishRelay.create<List<PodcastFull>>()

    fun observeSubscriptions(): Observable<List<PodcastFull>> {
        return Observable.merge(
            subscriptionsRelay.hide(),
            Observable.just(all)
        )
    }

    fun add(podcastFull: PodcastFull) {
        put(podcastFull.id, podcastFull)
        subscriptionsRelay.accept(all)
    }

    fun remove(podcastFull: PodcastFull) {
        remove(podcastFull.id)
        subscriptionsRelay.accept(all)
    }

    fun getSubscriptions(): List<PodcastFull> {
        return all
    }

    fun getSubscribedIds(): List<String> {
        return all.map(PodcastFull::id)
    }
}

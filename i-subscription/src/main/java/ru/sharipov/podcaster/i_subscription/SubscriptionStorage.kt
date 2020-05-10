package ru.sharipov.podcaster.i_subscription

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Single
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage


class SubscriptionStorage(
    cacheDir: String,
    namingProcessor: NamingProcessor
) : BaseJsonFileStorage<Subscription>(
    FileProcessor(cacheDir, STORAGE_DIR, STORAGE_MAX_FILES),
    namingProcessor,
    Subscription::class.java
) {

    private companion object {
        const val STORAGE_DIR = "cart_storage"
        const val STORAGE_MAX_FILES = 255
    }

    private val subscriptionsRelay = PublishRelay.create<List<Subscription>>()

    fun observeSubscriptions(): Observable<List<Subscription>> {
        return Observable.merge(
            subscriptionsRelay.hide(),
            Observable.just(all)
        )
    }

    fun add(subscription: Subscription) {
        put(subscription.id, subscription)
        subscriptionsRelay.accept(all)
    }

    fun remove(subscription: Subscription) {
        remove(subscription.id)
        subscriptionsRelay.accept(all)
    }

    fun getSubscriptions(): List<Subscription> {
        return all
    }

    fun getSubscribedIds(): List<String> {
        return all.map(Subscription::id)
    }
}

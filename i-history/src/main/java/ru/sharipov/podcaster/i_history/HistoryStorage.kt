package ru.sharipov.podcaster.i_history

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage

class HistoryStorage(
    cacheDir: String,
    namingProcessor: NamingProcessor
) : BaseJsonFileStorage<Episode>(
    FileProcessor(cacheDir, STORAGE_DIR, STORAGE_MAX_FILES),
    namingProcessor,
    Episode::class.java
) {

    private companion object {
        const val STORAGE_DIR = "podcast_history_storage"
        const val STORAGE_MAX_FILES = Integer.MAX_VALUE
    }

    private val historyRelay = BehaviorRelay.create<List<Episode>>()

    fun observeHistory(): Observable<List<Episode>> {
        return Observable.merge(
            historyRelay.hide(),
            Observable.just(all)
        )
    }

    fun add(media: Episode) {
        put(media.id, media)
    }

}

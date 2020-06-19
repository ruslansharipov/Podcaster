package ru.sharipov.podcaster.i_history

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Single
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage

/**
 * The storage of playback progress
 */
class ProgressStorage(
    cacheDir: String,
    namingProcessor: NamingProcessor
) : BaseJsonFileStorage<Int>(
    FileProcessor(cacheDir, STORAGE_DIR, 1),
    namingProcessor,
    Int::class.java
) {

    private companion object {
        const val STORAGE_DIR = "position_storage"
    }

    private val progressRelay = PublishRelay.create<ProgressItem>()

    /**
     * Observe playback progress changes of the Episode with given [episodeId]
     */
    fun observeProgressChanges(episodeId: String): Observable<Int> {
        return Observable.merge(
            Observable.just(getSavedProgress(episodeId)),
            progressRelay
                .hide()
                .filter { it.episodeId == episodeId }
                .map(ProgressItem::progressSec)
        )
    }

    /**
     * Save playback progress
     *
     * @param id            id of the episode
     * @param positionSec   position in seconds
     */
    fun saveProgress(id: String, positionSec: Int) {
        put(id, positionSec)
        progressRelay.accept(ProgressItem(id, positionSec))
    }

    /**
     * @return saved playback progress of the episode with given [id] synchronously
     * Shouldn't be called on the main thread
     */
    fun getSavedProgress(id: String) : Int {
        return get(id) ?: 0
    }

    /**
     * @return saved playback progress of the episode with given [id] wrapped in [Single]
     */
    fun getSavedProgressSingle(id: String) : Single<Int> {
        return Single.fromCallable { getSavedProgress(id) }
    }
}
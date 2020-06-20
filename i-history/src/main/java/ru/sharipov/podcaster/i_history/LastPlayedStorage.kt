package ru.sharipov.podcaster.i_history

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage

/**
 * The storage of the last played episode
 */
class LastPlayedStorage(
    cacheDir: String,
    namingProcessor: NamingProcessor
) : BaseJsonFileStorage<Episode>(
    FileProcessor(cacheDir, STORAGE_DIR, 1),
    namingProcessor,
    Episode::class.java
) {

    private companion object {
        const val STORAGE_DIR = "last_played_storage"
        const val STORAGE_KEY = "last_played_key"
    }

    /**
     * @returns true if the storage contains any played episode
     */
    val isNotEmpty: Boolean
        get() = contains(STORAGE_KEY)

    private val lastPlayedRelay: BehaviorRelay<Episode> = if (isNotEmpty) {
        BehaviorRelay.createDefault(get(STORAGE_KEY)!!)
    } else {
        BehaviorRelay.create()
    }

    /**
     * Save last played episode
     *
     * @param media episode to save
     */
    fun put(media: Episode) {
        put(STORAGE_KEY, media)
        lastPlayedRelay.accept(media)
    }

    /**
     * Get the last played episode synchronously
     * Shouldn't be called on the main thread
     */
    fun getLastPlayed(): Episode? {
        return get(STORAGE_KEY)
    }

    /**
     * Observe last played episode
     */
    fun observeLastPlayed(): Observable<Episode> {
        return lastPlayedRelay.hide()
    }

}

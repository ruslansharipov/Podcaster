package ru.sharipov.podcaster.i_history

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage

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

    val isNotEmpty: Boolean
        get() = contains(STORAGE_KEY)

    private val lastPlayedRelay = if (isNotEmpty) {
        BehaviorRelay.createDefault(get(STORAGE_KEY)!!)
    } else {
        BehaviorRelay.create()
    }

    fun put(media: Episode) {
        put(STORAGE_KEY, media)
        lastPlayedRelay.accept(media)
    }

    fun observeLastPlayed(): Observable<Episode> {
        return lastPlayedRelay.hide()
    }

}

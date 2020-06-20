package ru.sharipov.podcaster.i_history

import io.reactivex.Observable
import io.reactivex.Single
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

/**
 * Interactor for saving and restoring playback progress, saving played episodes
 * and observing the last played episode
 */
@PerApplication
class HistoryInteractor @Inject constructor(
    private val historyStorage: HistoryStorage,
    private val lastPlayedStorage: LastPlayedStorage,
    private val progressStorage: ProgressStorage
) {

    /**
     * Add episode to the playback history
     *
     * @param media episode to add
     */
    fun add(media: Episode) {
        historyStorage.add(media)
        lastPlayedStorage.put(media)
    }

    /**
     * Observe the history of playback
     */
    fun observeHistory(): Observable<List<Episode>> {
        return historyStorage.observeHistory()
    }

    /**
     * Observe the last played episode
     */
    fun observeLastPlayed(): Observable<Episode> {
        return lastPlayedStorage.observeLastPlayed()
    }

    /**
     * @return  true if the storage contains any played episode
     */
    fun hasLastPlayed() : Boolean = lastPlayedStorage.isNotEmpty

    /**
     * Save playback progress
     *
     * @param positionSec   progress position in seconds
     */
    fun saveProgress(positionSec: Int) {
        val id = lastPlayedStorage.getLastPlayed()?.id
        if (id != null){
            progressStorage.saveProgress(id, positionSec)
        }
    }

    /**
     * @return saved progress of the episode with given [episodeId] wrapped into [Single]
     */
    fun getProgressSingle(episodeId: String): Single<Int> {
        return progressStorage.getSavedProgressSingle(episodeId)
    }

    /**
     * Synchronously gets the saved progress of the episode with given [episodeId]
     */
    fun getProgress(episodeId: String): Int {
        return progressStorage.getSavedProgress(episodeId)
    }

    /**
     * Observe the position of last played episode
     */
    fun observePosition(): Observable<Int> {
        return observeLastPlayed()
            .switchMap { lastPlayed: Episode ->
                progressStorage.observeProgressChanges(lastPlayed.id)
            }
    }

}
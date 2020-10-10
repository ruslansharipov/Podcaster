package ru.sharipov.podcaster.i_history

import io.reactivex.Completable
import io.reactivex.Flowable
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
    private val repository: HistoryRepository
) {

    /**
     * Add episode to the playback history
     *
     * @param media episode to add
     */
    fun saveProgress(
        episode: Episode,
        progressSec: Int,
        lastPlayedTime: Long
    ): Completable {
        return repository.updatePlayedHistory(episode, progressSec, lastPlayedTime)
    }

    /**
     * Observe the history of playback
     */
    fun observeHistory(): Flowable<List<Episode>> {
        return repository.observePlayedEpisodes()
    }

    /**
     * Observe the last played episode
     */
    fun observeLastPlayed(): Flowable<Episode> {
        return repository.observeLastPlayedEpisode()
    }

    /**
     * Gets the saved progress of the episode with given [episodeId]
     */
    fun getProgress(episodeId: String): Single<Int> {
        return repository.getSavedProgress(episodeId)
    }

    /**
     * Observe the position of last played episode
     */
    fun observePosition(): Flowable<Int> {
        return observeLastPlayed()
            .map { it.progress }
    }

}
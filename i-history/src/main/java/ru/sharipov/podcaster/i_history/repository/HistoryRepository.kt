package ru.sharipov.podcaster.i_history.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.i_network.network.transform
import ru.sharipov.podcaster.i_network.network.transformCollection
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class HistoryRepository @Inject constructor(
    private val historyDao: HistoryDao
) {

    fun observePlayedEpisodes(): Flowable<List<Episode>> {
        return historyDao
            .getHistoryEntities()
            .transformCollection()
    }

    fun updatePlayedHistory(
        episode: Episode,
        progress: Int,
        lastPlayedTime: Long
    ): Completable {
        return historyDao.updatePlayedHistory(
            episode.toHistoryEntity(progress, lastPlayedTime)
        )
    }

    fun observeLastPlayedEpisode(): Flowable<Episode>{
        return historyDao.getLastPlayed().transform()
    }

    fun getSavedProgress(episodeId: String): Single<Int> {
        return historyDao
            .getSavedProgress(episodeId)
            .toSingle(0)
    }
}

fun Episode.toHistoryEntity(progress: Int, lastPlayedTime: Long): HistoryEntity {
    return HistoryEntity(
        podcastTitle = podcastTitle,
        podcastImage = podcastImage,
        maybeAudioInvalid = maybeAudioInvalid,
        pubDateMs = pubDateMs,
        streamUrl = streamUrl,
        listennotesEditUrl = listennotesEditUrl,
        image = image,
        thumbnail = thumbnail,
        description = description,
        title = title,
        explicitContent = explicitContent,
        listennotesUrl = listennotesUrl,
        duration = duration,
        id = id,
        link = link,
        lastPlayedTime = lastPlayedTime,
        progress = progress
    )
}
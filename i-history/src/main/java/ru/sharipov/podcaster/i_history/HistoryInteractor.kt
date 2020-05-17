package ru.sharipov.podcaster.i_history

import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Episode
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class HistoryInteractor @Inject constructor(
    private val historyStorage: HistoryStorage,
    private val lastPlayedStorage: LastPlayedStorage
) {

    fun add(media: Episode) {
        historyStorage.add(media)
        lastPlayedStorage.put(media)
    }

    fun observeHistory(): Observable<List<Episode>> {
        return historyStorage.observeHistory()
    }

    fun observeLastPlayed(): Observable<Episode> {
        return lastPlayedStorage.observeLastPlayed()
    }

    fun hasLastPlayed() : Boolean = lastPlayedStorage.isNotEmpty

}
package ru.sharipov.podcaster.i_listen

import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.i_network.network.transform
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class PodcastRepository @Inject constructor(
    private val listenApi: ListenApi
) {

    fun getGenres(queryMode: Int): Observable<List<Genre>> {
        return listenApi
            .getGenres(queryMode)
            .transform()
            .toObservable()
    }
}
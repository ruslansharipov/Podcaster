package ru.sharipov.podcaster.i_listen

import io.reactivex.Observable
import io.reactivex.Single
import ru.sharipov.podcaster.domain.CuratedItem
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.Podcast
import ru.sharipov.podcaster.i_network.network.transform
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import javax.inject.Inject

@PerApplication
class PodcastRepository @Inject constructor(
    private val listenApi: ListenApi
) {

    fun getCuratedPodcasts(page: Int): Observable<DataList<CuratedItem>> {
        return listenApi
            .getCuratedPodcasts(page)
            .transform()
            .toObservable()
    }

    fun getGenres(queryMode: Int): Observable<List<Genre>> {
        return listenApi
            .getGenres(queryMode)
            .transform()
            .toObservable()
    }

    fun getBestPodcasts(page: Int, region: String?, genreId: Int?) : Single<DataList<Podcast>> {
        return listenApi
            .getBestPodcasts(page, region, genreId)
            .transform()
    }
}
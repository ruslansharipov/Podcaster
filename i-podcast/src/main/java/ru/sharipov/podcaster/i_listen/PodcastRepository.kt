package ru.sharipov.podcaster.i_listen

import io.reactivex.Observable
import io.reactivex.Single
import ru.sharipov.podcaster.domain.*
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

    fun getBestPodcasts(page: Int, region: String?, genreId: Int?): Single<DataList<PodcastFull>> {
        return listenApi
            .getBestPodcasts(page, region, genreId)
            .transform()
    }

    fun getTypeAhead(
        query: String,
        showPodcasts: Boolean,
        showGenres: Boolean
    ): Observable<TypeAhead> {
        return listenApi
            .getTypeAhead(
                query = query,
                showPodcasts = if (showPodcasts) 1 else 0,
                showGenres = if (showGenres) 1 else 0
            )
            .transform()
            .toObservable()
    }

    fun getPodcastEpisodes(
        id: String,
        nextEpisodePubDate: Long,
        sortType: SortType
    ): Observable<PodcastFull> {
        return listenApi
            .getPodcast(id, nextEpisodePubDate, sortType.id)
            .transform()
            .toObservable()
    }
}
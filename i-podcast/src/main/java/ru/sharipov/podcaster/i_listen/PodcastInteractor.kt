package ru.sharipov.podcaster.i_listen

import io.reactivex.Observable
import io.reactivex.Single
import ru.sharipov.podcaster.base.datalist_date.MergeList
import ru.sharipov.podcaster.domain.*
import ru.sharipov.podcaster.i_network.network.BaseNetworkInteractor
import ru.sharipov.podcaster.i_network.network.transform
import ru.sharipov.podcaster.i_subscription.SubscriptionInteractor
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import javax.inject.Inject

@PerApplication
class PodcastInteractor @Inject constructor(
    connectionProvider: ConnectionProvider,
    private val listenApi: ListenApi
) : BaseNetworkInteractor(connectionProvider) {

    fun getCuratedPodcasts(page: Int): Observable<DataList<CuratedItem>> {
        return listenApi
            .getCuratedPodcasts(page)
            .transform()
            .toObservable()
    }

    fun getGenres(): Observable<List<Genre>> {
        return hybridQueryWithSimpleCache { queryMode: Int ->
            listenApi
                .getGenres(queryMode)
                .transform()
                .toObservable()
        }
    }

    fun getBestPodcasts(
        page: Int,
        region: String? = null,
        genreId: Int? = null
    ): Single<DataList<PodcastFull>> {
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
        podcastId: String,
        podcastTitle: String,
        sortType: SortType,
        nextEpisodePubDate: Long?
    ): Observable<MergeList<Episode>> {
        return listenApi
            .getPodcastEpisodes(podcastId, sortType.id, nextEpisodePubDate)
            .map { it.transform(podcastTitle) }
            .toObservable()
    }

    fun getPodcast(podcastId: String): Observable<PodcastFull> {
        return listenApi
            .getPodcast(podcastId)
            .transform()
            .toObservable()
    }
}
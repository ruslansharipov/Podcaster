package ru.sharipov.podcaster.i_listen

import io.reactivex.Observable
import io.reactivex.Single
import ru.sharipov.podcaster.domain.CuratedItem
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.domain.Podcast
import ru.sharipov.podcaster.i_network.network.BaseNetworkInteractor
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import javax.inject.Inject

@PerApplication
class PodcastInteractor @Inject constructor(
    connectionProvider: ConnectionProvider,
    private val podcastRepository: PodcastRepository
): BaseNetworkInteractor(connectionProvider) {

    fun getCuratedPodcasts(page: Int): Observable<DataList<CuratedItem>> {
        return podcastRepository.getCuratedPodcasts(page)
    }

    fun getGenres(): Observable<List<Genre>> {
        return hybridQueryWithSimpleCache(podcastRepository::getGenres)
    }

    fun getBestPodcasts(
        page: Int,
        region: String? = null,
        genreId: Int? = null
    ) : Single<DataList<Podcast>> {
        return podcastRepository.getBestPodcasts(page, region, genreId)
    }
}
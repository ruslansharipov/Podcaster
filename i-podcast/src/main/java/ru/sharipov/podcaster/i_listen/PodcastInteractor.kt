package ru.sharipov.podcaster.i_listen

import io.reactivex.Observable
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.i_network.network.BaseNetworkInteractor
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

@PerApplication
class PodcastInteractor @Inject constructor(
    connectionProvider: ConnectionProvider,
    private val podcastRepository: PodcastRepository
): BaseNetworkInteractor(connectionProvider) {

    fun getGenres(): Observable<List<Genre>> {
        return hybridQueryWithSimpleCache(podcastRepository::getGenres)
    }
}
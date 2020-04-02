package ru.sharipov.podcaster.i_listen

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.sharipov.podcaster.i_listen.response.CuratedResponse
import ru.sharipov.podcaster.i_network.ServerUrls

interface ListenApi {

    @GET(ServerUrls.CURATED_PODCASTS)
    fun getBestPodcasts(
        @Query("page") page: Int
    ): Single<CuratedResponse>
}
package ru.sharipov.podcaster.i_listen

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.sharipov.podcaster.i_listen.response.CuratedResponse
import ru.sharipov.podcaster.i_listen.response.GenresResponse
import ru.sharipov.podcaster.i_network.ServerUrls
import ru.sharipov.podcaster.i_network.network.BaseServerConstants

interface ListenApi {

    @GET(ServerUrls.CURATED_PODCASTS)
    fun getBestPodcasts(
        @Query("page") page: Int,
        @Query("region") region: String? = null,
        @Query("genre_id") genreId: Int? = null
    ): Single<CuratedResponse>

    /**
     * Получение жанров
     *
     * @param topLevelOnly 1 если нужны только основные жанры, 0 если нужны поджанры, по умолчанию 0
     */
    @GET(ServerUrls.GENRES)
    fun getGenres(
        @Header(BaseServerConstants.HEADER_QUERY_MODE) queryMode: Int,
        @Query("top_level_only") topLevelOnly: Int = 0
    ) : Single<GenresResponse>
}
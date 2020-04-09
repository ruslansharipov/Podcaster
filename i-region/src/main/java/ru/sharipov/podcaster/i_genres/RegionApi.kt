package ru.sharipov.podcaster.i_genres

import io.reactivex.Single
import retrofit2.http.GET
import ru.sharipov.podcaster.i_network.ServerUrls

interface RegionApi {

    @GET(ServerUrls.REGIONS)
    fun getRegions(): Single<RegionsResponse>

}
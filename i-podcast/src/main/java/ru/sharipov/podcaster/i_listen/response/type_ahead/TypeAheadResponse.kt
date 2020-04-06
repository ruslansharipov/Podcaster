package ru.sharipov.podcaster.i_listen.response.type_ahead

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.TypeAhead
import ru.sharipov.podcaster.i_listen.response.GenreResponse
import ru.sharipov.podcaster.i_network.network.Transformable
import ru.sharipov.podcaster.i_network.network.transformCollection

data class TypeAheadResponse(
    @SerializedName("terms") val terms: List<String>?,
    @SerializedName("genres") val genres: List<GenreResponse>?,
    @SerializedName("podcasts") val podcasts: List<PodcastTypeAheadResponse>?
) : Transformable<TypeAhead> {

    override fun transform(): TypeAhead {
        return TypeAhead(
            terms = terms ?: emptyList(),
            genres = genres.transformCollection(),
            podcasts = podcasts.transformCollection()
        )
    }
}
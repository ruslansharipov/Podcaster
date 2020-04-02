package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.i_network.network.Transformable
import ru.sharipov.podcaster.i_network.network.transformCollection

data class GenresResponse(
    @SerializedName("genres") val genres: List<GenreResponse>?
): Transformable<List<Genre>> {

    override fun transform(): List<Genre> {
        return genres.transformCollection()
    }
}
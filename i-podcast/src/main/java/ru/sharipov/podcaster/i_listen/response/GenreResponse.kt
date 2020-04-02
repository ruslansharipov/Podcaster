package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.EMPTY_STRING
import ru.sharipov.podcaster.domain.Genre
import ru.sharipov.podcaster.i_network.network.Transformable

data class GenreResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("parent_id") val parentId: Int?,
    @SerializedName("name") val name: String?
) : Transformable<Genre> {

    override fun transform(): Genre {
        return Genre(
            id = id ?: 0,
            parentId = parentId ?: 0,
            name = name ?: EMPTY_STRING
        )
    }
}
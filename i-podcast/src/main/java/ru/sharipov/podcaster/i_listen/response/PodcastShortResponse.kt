package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.PodcastShort
import ru.sharipov.podcaster.i_network.network.Transformable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class PodcastShortResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("listennotes_url") val listenNotesUrl: String?
) : Transformable<PodcastShort> {

    override fun transform(): PodcastShort {
        return PodcastShort(
            id ?: EMPTY_STRING,
            image ?: EMPTY_STRING,
            title ?: EMPTY_STRING,
            publisher ?: EMPTY_STRING,
            thumbnail ?: EMPTY_STRING,
            listenNotesUrl ?: EMPTY_STRING
        )
    }
}
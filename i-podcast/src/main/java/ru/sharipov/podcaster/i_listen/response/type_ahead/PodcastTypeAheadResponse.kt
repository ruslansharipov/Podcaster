package ru.sharipov.podcaster.i_listen.response.type_ahead

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.EMPTY_STRING
import ru.sharipov.podcaster.domain.PodcastTypeAhead
import ru.sharipov.podcaster.i_network.network.Transformable

data class PodcastTypeAheadResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("title_original") val titleOriginal: String?,
    @SerializedName("title_highlighted") val titleHighlighted: String?,
    @SerializedName("publisher_original") val publisherOriginal: String?,
    @SerializedName("publisher_highlighted") val publisherHighlighted: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("explicit_content") val explicitContent: Boolean?
) : Transformable<PodcastTypeAhead> {

    override fun transform(): PodcastTypeAhead {
        return PodcastTypeAhead(
            id = id ?: EMPTY_STRING,
            publisherOriginal = publisherOriginal ?: EMPTY_STRING,
            publisherHighlighted = publisherHighlighted ?: EMPTY_STRING,
            titleOriginal = titleOriginal ?: EMPTY_STRING,
            titleHighlighted = titleHighlighted ?: EMPTY_STRING,
            thumbnail = thumbnail ?: EMPTY_STRING,
            explicitContent = explicitContent ?: false,
            image = image ?: EMPTY_STRING
        )
    }
}
package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.CuratedItem
import ru.sharipov.podcaster.i_network.network.Transformable
import ru.sharipov.podcaster.i_network.network.transformCollection
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class CuratedItemResponse(
    @SerializedName("description") val description: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("listennotes_url") val listenNotesUrl: String?,
    @SerializedName("podcasts") val podcasts: List<PodcastShortResponse>?,
    @SerializedName("pub_date_ms") val pubDateMs: Long?,
    @SerializedName("source_domain") val sourceDomain: String?,
    @SerializedName("source_url") val sourceUrl: String?,
    @SerializedName("title") val title: String?
) : Transformable<CuratedItem> {

    override fun transform(): CuratedItem {
        return CuratedItem(
            description ?: EMPTY_STRING,
            id ?: EMPTY_STRING,
            listenNotesUrl ?: EMPTY_STRING,
            podcasts?.transformCollection() ?: emptyList(),
            pubDateMs ?: 0,
            sourceDomain ?: EMPTY_STRING,
            sourceUrl ?: EMPTY_STRING,
            title ?: EMPTY_STRING
        )
    }
}
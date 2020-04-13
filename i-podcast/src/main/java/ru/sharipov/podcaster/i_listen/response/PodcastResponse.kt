package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.Extra
import ru.sharipov.podcaster.domain.LookingFor
import ru.sharipov.podcaster.domain.PodcastFull
import ru.sharipov.podcaster.i_network.network.Transformable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class PodcastResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("is_claimed") val isClaimed: Boolean?,
    @SerializedName("explicit_content") val explicitContent: Boolean?,
    @SerializedName("website") val website: String?,
    @SerializedName("total_episodes") val totalEpisodes: Int?,
    @SerializedName("earliest_pub_date_ms") val earliestPubDateMs: Long?,
    @SerializedName("rss") val rss: String?,
    @SerializedName("latest_pub_date_ms") val latestPubDateMs: Long?,
    @SerializedName("title") val title: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("listennotes_url") val listennotesUrl: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("itunes_id") val itunesId: Int?,
    @SerializedName("looking_for") val lookingFor: LookingForResponse?,
    @SerializedName("extra") val extra: ExtraResponse?,
    @SerializedName("genre_ids") val genreIds: List<Int>?
) : Transformable<PodcastFull> {

    override fun transform(): PodcastFull {
        return PodcastFull(
            id = id ?: EMPTY_STRING,
            isClaimed = isClaimed ?: false,
            explicitContent = explicitContent ?: false,
            website = website ?: EMPTY_STRING,
            totalEpisodes = totalEpisodes ?: 0,
            earliestPubDateMs = earliestPubDateMs ?: 0,
            rss = rss ?: EMPTY_STRING,
            title = title ?: EMPTY_STRING,
            language = language ?: EMPTY_STRING,
            description = description ?: EMPTY_STRING,
            email = email ?: EMPTY_STRING,
            image = image ?: EMPTY_STRING,
            thumbnail = thumbnail ?: EMPTY_STRING,
            country = country ?: EMPTY_STRING,
            publisher = publisher ?: EMPTY_STRING,
            itunesId = itunesId ?: 0,
            lookingFor = lookingFor?.transform() ?: LookingFor(),
            extra = extra?.transform() ?: Extra(),
            genreIds = genreIds ?: emptyList(),
            listenNotesUrl = listennotesUrl ?: EMPTY_STRING,
            latestPubDateMs = latestPubDateMs ?: 0
        )
    }
}
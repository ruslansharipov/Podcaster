package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.EMPTY_STRING
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.i_network.network.Transformable

data class EpisodeResponse(
    @SerializedName("maybe_audio_invalid") val maybeAudioInvalid: Boolean?,
    @SerializedName("pub_date_ms") val pubDateMs: Long?,
    @SerializedName("audio") val audio: String?,
    @SerializedName("listennotes_edit_url") val listennotesEditUrl: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("explicit_content") val explicitContent: Boolean?,
    @SerializedName("listennotes_url") val listennotesUrl: String?,
    @SerializedName("audio_length_sec") val audioLengthSec: Int?,
    @SerializedName("id") val id: String?,
    @SerializedName("link") val link: String?
) : Transformable<Episode> {

    override fun transform(): Episode {
        return Episode(
            maybeAudioInvalid = maybeAudioInvalid ?: false,
            pubDateMs = pubDateMs ?: 0,
            streamUrl = audio ?: EMPTY_STRING,
            listennotesEditUrl = listennotesEditUrl ?: EMPTY_STRING,
            image = image ?: EMPTY_STRING,
            thumbnail = thumbnail ?: EMPTY_STRING,
            description = description ?: EMPTY_STRING,
            title = title ?: EMPTY_STRING,
            explicitContent = explicitContent ?: false,
            listennotesUrl = listennotesUrl ?: EMPTY_STRING,
            duration = audioLengthSec ?: 0,
            id = id ?: EMPTY_STRING,
            link = link ?: EMPTY_STRING
        )
    }
}
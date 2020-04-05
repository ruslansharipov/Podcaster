package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.domain.Extra
import ru.sharipov.podcaster.i_network.network.Transformable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

data class ExtraResponse(
    @SerializedName("youtube_url") val youtubeUrl: String?,
    @SerializedName("facebook_handle") val facebookHandle: String?,
    @SerializedName("instagram_handle") val instagramHandle: String?,
    @SerializedName("twitter_handle") val twitterHandle: String?,
    @SerializedName("wechat_handle") val wechatHandle: String?,
    @SerializedName("patreon_handle") val patreonHandle: String?,
    @SerializedName("google_url") val googleUrl: String?,
    @SerializedName("linkedin_url") val linkedinUrl: String?,
    @SerializedName("spotify_url") val spotifyUrl: String?,
    @SerializedName("url1") val url1: String?,
    @SerializedName("url2") val url2: String?,
    @SerializedName("url3") val url3: String?
) : Transformable<Extra> {

    override fun transform(): Extra {
        return Extra(
            youtubeUrl = youtubeUrl ?: EMPTY_STRING,
            facebookHandle = facebookHandle ?: EMPTY_STRING,
            instagramHandle = instagramHandle ?: EMPTY_STRING,
            twitterHandle = twitterHandle ?: EMPTY_STRING,
            wechatHandle = wechatHandle ?: EMPTY_STRING,
            patreonHandle = patreonHandle ?: EMPTY_STRING,
            googleUrl = googleUrl ?: EMPTY_STRING,
            linkedinUrl = linkedinUrl ?: EMPTY_STRING,
            spotifyUrl = spotifyUrl ?: EMPTY_STRING,
            url1 = url1 ?: EMPTY_STRING,
            url2 = url2 ?: EMPTY_STRING,
            url3 = url3 ?: EMPTY_STRING
        )
    }
}
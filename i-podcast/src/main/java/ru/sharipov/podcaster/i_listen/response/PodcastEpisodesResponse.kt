package ru.sharipov.podcaster.i_listen.response

import com.google.gson.annotations.SerializedName
import ru.sharipov.podcaster.base.datalist_date.MergeList
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.i_network.network.Transformable
import ru.sharipov.podcaster.i_network.network.transformCollection

data class PodcastEpisodesResponse(
    @SerializedName("episodes") val episodes: List<EpisodeResponse>?,
    @SerializedName("latest_pub_date_ms") val latestPubDateMs: Long?,
    @SerializedName("earliest_pub_date_ms") val earliestPubDateMs: Long?,
    @SerializedName("next_episode_pub_date") val nextEpisodePubDate: Long?,
    @SerializedName("total_episodes") val totalEpisodes: Int?
) {

    fun transform(podcastTitle: String, podcastImage: String): MergeList<Episode> {
        return MergeList(
            data = episodes?.map { it.transform(podcastTitle, podcastImage) } ?: emptyList(),
            earliestPubDateMs = earliestPubDateMs ?: 0,
            latestPubDateMs = latestPubDateMs ?: 0,
            nextEpisodePubDate = nextEpisodePubDate ?: 0
        )
    }
}
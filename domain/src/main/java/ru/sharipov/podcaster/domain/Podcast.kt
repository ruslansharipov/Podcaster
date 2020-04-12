package ru.sharipov.podcaster.domain

data class Podcast(
    val id: String = EMPTY_STRING,
    val isClaimed: Boolean?,
    val explicitContent: Boolean?,
    val website: String = EMPTY_STRING,
    val totalEpisodes: Int = 0,
    val earliestPubDateMs: Long = 0,
    val rss: String = EMPTY_STRING,
    val latestPubDateMs: Long = 0,
    val title: String = EMPTY_STRING,
    val language: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val image: String = EMPTY_STRING,
    val thumbnail: String = EMPTY_STRING,
    val listennotesUrl: String = EMPTY_STRING,
    val country: String = EMPTY_STRING,
    val publisher: String = EMPTY_STRING,
    val itunesId: Int = 0,
    val lookingFor: LookingFor = LookingFor(),
    val extra: Extra = Extra(),
    val genreIds: List<Int?> = emptyList(),
    val episodes: List<Episode> = emptyList(),
    val nextEpisodePubDate: Long = 0
)
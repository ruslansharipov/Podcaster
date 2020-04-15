package ru.sharipov.podcaster.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PodcastFull(
    val id: String = EMPTY_STRING,
    val publisher: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val image: String = EMPTY_STRING,
    val thumbnail: String = EMPTY_STRING,
    val listenNotesUrl: String = EMPTY_STRING,
    val isClaimed: Boolean = false,
    val explicitContent: Boolean = false,
    val website: String = EMPTY_STRING,
    val totalEpisodes: Int = 0,
    val earliestPubDateMs: Long = 0,
    val rss: String = EMPTY_STRING,
    val latestPubDateMs: Long = 0,
    val language: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val country: String = EMPTY_STRING,
    val itunesId: Int = 0,
    val lookingFor: LookingFor = LookingFor(),
    val extra: Extra = Extra(),
    val genreIds: List<Int> = emptyList()
) : Parcelable {

    val isShort: Boolean get() = description.isEmpty() && country.isEmpty() && rss.isEmpty()
}
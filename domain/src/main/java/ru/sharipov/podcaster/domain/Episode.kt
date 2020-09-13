package ru.sharipov.podcaster.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    val id: String = EMPTY_STRING,
    val image: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val podcastTitle: String = EMPTY_STRING,
    val podcastImage: String = EMPTY_STRING,
    val streamUrl: String = EMPTY_STRING,
    val duration: Int = 0,
    val maybeAudioInvalid: Boolean = false,
    val pubDateMs: Long = 0,
    val listennotesEditUrl: String = EMPTY_STRING,
    val thumbnail: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val explicitContent: Boolean = false,
    val listennotesUrl: String = EMPTY_STRING,
    val link: String = EMPTY_STRING
) : Comparable<Episode>, Parcelable {

    override fun compareTo(other: Episode): Int {
        return (other.pubDateMs - pubDateMs).toInt()
    }

}
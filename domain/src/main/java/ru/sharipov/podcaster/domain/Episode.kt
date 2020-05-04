package ru.sharipov.podcaster.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.sharipov.podcaster.domain.player.Media

@Parcelize
data class Episode(
    override val id: String = EMPTY_STRING,
    override val image: String = EMPTY_STRING,
    override val title: String = EMPTY_STRING,
    override val streamUrl: String = EMPTY_STRING,
    override val duration: Int = 0,
    val maybeAudioInvalid: Boolean = false,
    val pubDateMs: Long = 0,
    val listennotesEditUrl: String = EMPTY_STRING,
    val thumbnail: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val explicitContent: Boolean = false,
    val listennotesUrl: String = EMPTY_STRING,
    val link: String = EMPTY_STRING
) : Comparable<Episode>, Parcelable, Media {

    override fun compareTo(other: Episode): Int {
        return (other.pubDateMs - pubDateMs).toInt()
    }

}
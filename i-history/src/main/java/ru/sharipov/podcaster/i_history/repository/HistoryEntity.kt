package ru.sharipov.podcaster.i_history.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.sharipov.podcaster.domain.Episode
import ru.sharipov.podcaster.i_network.network.Transformable

@Entity(tableName = "history_entity")
data class HistoryEntity(
    @PrimaryKey val id: String,
    val image: String,
    val title: String,
    val podcastTitle: String,
    val podcastImage: String,
    val streamUrl: String,
    val duration: Int,
    val maybeAudioInvalid: Boolean,
    val pubDateMs: Long,
    val listennotesEditUrl: String,
    val thumbnail: String,
    val description: String,
    val explicitContent: Boolean,
    val listennotesUrl: String,
    val link: String,
    val lastPlayedTime: Long,
    val progress: Int
) : Transformable<Episode> {

    override fun transform(): Episode {
        return Episode(
            podcastTitle = podcastTitle,
            podcastImage = podcastImage,
            maybeAudioInvalid = maybeAudioInvalid,
            pubDateMs = pubDateMs,
            streamUrl = streamUrl,
            listennotesEditUrl = listennotesEditUrl,
            image = image,
            thumbnail = thumbnail,
            description = description,
            title = title,
            explicitContent = explicitContent,
            listennotesUrl = listennotesUrl,
            duration = duration,
            id = id,
            link = link,
            lastPlayedTime = lastPlayedTime,
            progress = progress
        )
    }
}
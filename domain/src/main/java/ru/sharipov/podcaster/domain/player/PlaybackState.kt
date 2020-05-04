package ru.sharipov.podcaster.domain.player

sealed class PlaybackState {

    object Idle : PlaybackState()
    object Stopped : PlaybackState()

    data class Buffering(override val media: Media?) : PlaybackState(), MediaState
    data class Playing(override val media: Media?) : PlaybackState(), MediaState
    data class Paused(override val media: Media?) : PlaybackState(), MediaState
    data class Completed(override val media: Media?) : PlaybackState(), MediaState
}
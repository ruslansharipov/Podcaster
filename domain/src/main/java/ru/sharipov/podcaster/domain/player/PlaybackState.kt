package ru.sharipov.podcaster.domain.player

sealed class PlaybackState {

    object Idle : PlaybackState()
    object Stopped : PlaybackState()
    data class Buffering(var media: Media?) : PlaybackState()
    data class Playing(var media: Media?) : PlaybackState()
    data class Paused(var media: Media?) : PlaybackState()
    data class Completed(var media: Media?) : PlaybackState()
}
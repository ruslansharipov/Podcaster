package ru.sharipov.podcaster.domain.player

import ru.sharipov.podcaster.domain.Episode

sealed class PlaybackState {

    object Idle : PlaybackState()
    object Stopped : PlaybackState()

    data class Buffering(override val media: Episode?) : PlaybackState(), MediaState
    data class Playing(override val media: Episode?) : PlaybackState(), MediaState
    data class Paused(override val media: Episode?) : PlaybackState(), MediaState
    data class Completed(override val media: Episode?) : PlaybackState(), MediaState
}
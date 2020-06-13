package ru.sharipov.podcaster.f_player.playback

import ru.sharipov.podcaster.domain.Episode

/* The base interface for any playback implementation */
interface PlayerInterface {

    val isPlaying: Boolean

    val bufferedPositionMs: Long

    val positionMs: Long

    val duration: Long

    fun play(media: Episode?)

    fun pause()

    fun complete()

    fun stop()

    fun seekTo(positionMs: Long)

    fun invalidateCurrent()
}
package ru.sharipov.podcaster.domain

data class Episode(
    val maybeAudioInvalid: Boolean = false,
    val pubDateMs: Long = 0,
    val audio: String = EMPTY_STRING,
    val listennotesEditUrl: String = EMPTY_STRING,
    val image: String = EMPTY_STRING,
    val thumbnail: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val explicitContent: Boolean = false,
    val listennotesUrl: String = EMPTY_STRING,
    val audioLengthSec: Int = 0,
    val id: String = EMPTY_STRING,
    val link: String = EMPTY_STRING
)
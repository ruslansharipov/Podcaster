package ru.sharipov.podcaster.domain.player

data class QueueData(
    val media: List<Media>,
    val index: Int
)
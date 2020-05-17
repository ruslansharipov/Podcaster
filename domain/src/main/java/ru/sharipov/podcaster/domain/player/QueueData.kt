package ru.sharipov.podcaster.domain.player

import ru.sharipov.podcaster.domain.Episode

data class QueueData(
    val media: List<Episode>,
    val index: Int
)
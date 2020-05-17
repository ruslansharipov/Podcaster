package ru.sharipov.podcaster.domain.player

import ru.sharipov.podcaster.domain.Episode

interface MediaState {
    val media: Episode?
}
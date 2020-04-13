package ru.sharipov.podcaster.domain

data class PodcastShort(
    val id: String = EMPTY_STRING,
    val image: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val publisher: String = EMPTY_STRING,
    val thumbnail: String = EMPTY_STRING,
    val listenNotesUrl: String = EMPTY_STRING
) {

    fun toFull(): PodcastFull {
        return PodcastFull(id, publisher, title, image, thumbnail, listenNotesUrl)
    }
}
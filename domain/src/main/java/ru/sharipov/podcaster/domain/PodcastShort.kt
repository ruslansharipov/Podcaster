package ru.sharipov.podcaster.domain

class PodcastShort(
    override val id: String = EMPTY_STRING,
    override val publisher: String = EMPTY_STRING,
    override val title: String = EMPTY_STRING,
    override val image: String = EMPTY_STRING
): Subscription {

    fun toPodcastFull(): PodcastFull {
        return PodcastFull(id, publisher, title, image)
    }
}
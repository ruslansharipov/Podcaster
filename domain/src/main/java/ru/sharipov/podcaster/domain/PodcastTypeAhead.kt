package ru.sharipov.podcaster.domain

data class PodcastTypeAhead(
    val id: String = EMPTY_STRING,
    val publisherHighlighted: String = EMPTY_STRING,
    val publisherOriginal: String = EMPTY_STRING,
    val explicitContent: Boolean = false,
    val image: String = EMPTY_STRING,
    val thumbnail: String = EMPTY_STRING,
    val titleHighlighted: String = EMPTY_STRING,
    val titleOriginal: String = EMPTY_STRING
)